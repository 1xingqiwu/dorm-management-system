package com.ylb.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ylb.dto.vo.StudentProfileVO;
import com.ylb.entity.SysNotice;
import com.ylb.entity.SysUser;
import com.ylb.entity.TbRepair;
import com.ylb.entity.TbRoom;
import com.ylb.mapper.*;
import com.ylb.service.AiService;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class AiServiceImpl implements AiService {

    @Autowired private SysUserMapper sysUserMapper;
    @Autowired private SysNoticeMapper noticeMapper;
    @Autowired private TbStudentMapper studentMapper;
    @Autowired private TbRoomMapper roomMapper;
    @Autowired private TbRepairMapper repairMapper; // 记得引入报修Mapper

    @Value("${ai.deepseek.api-key}")
    private String apiKey;

    @Value("${ai.deepseek.base-url}")
    private String apiUrl;

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ExecutorService executor = Executors.newCachedThreadPool();

    @Override
    public SseEmitter streamChat(String question) {
        SseEmitter emitter = new SseEmitter(0L);

        // 1. 获取当前用户身份
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SysUser user = sysUserMapper.selectByUsername(username);

        // 2. 核心升级：构建全量数据上下文 (RAG)
        // 不管问什么，先把他的底细查个底朝天，喂给 AI
        StringBuilder dataContext = new StringBuilder();
        dataContext.append("【当前用户信息】：\n");
        dataContext.append(String.format("- 姓名：%s (角色：%s)\n", user.getNickname(), getUserRoleName(user.getUserType())));

        // 如果是学生，查询他的宿舍专属数据
        if (user.getUserType() == 0) {
            try {
                // A. 查宿舍和余额
                StudentProfileVO profile = studentMapper.getProfileByUserId(user.getUserId());
                if (profile != null && profile.getRoomId() != null) {
                    TbRoom room = roomMapper.selectById(profile.getRoomId());
                    dataContext.append(String.format("- 宿舍号：%s\n", room.getRoomNumber()));
                    dataContext.append(String.format("- 剩余电费：%.2f 元\n", room.getElectricityBalance()));
                } else {
                    dataContext.append("- 宿舍状态：暂未分配宿舍\n");
                }

                // B. 查最近的一条报修记录
                List<TbRepair> repairs = repairMapper.selectListByStudent(user.getUserId());
                if (!repairs.isEmpty()) {
                    TbRepair lastRepair = repairs.get(0);
                    String statusStr = lastRepair.getStatus() == 0 ? "待处理" : "已修好";
                    dataContext.append(String.format("- 最近报修：%s (%s)，状态：%s\n",
                            lastRepair.getDescription(), lastRepair.getSubmitTime(), statusStr));
                } else {
                    dataContext.append("- 最近报修：无记录\n");
                }

            } catch (Exception e) {
                // 忽略数据查询异常，不影响聊天
                System.out.println("数据查询失败: " + e.getMessage());
            }
        }

        // C. 查最新的2条公告 (所有人都能看)
        List<SysNotice> notices = noticeMapper.selectList(null);
        if (!notices.isEmpty()) {
            dataContext.append("【最新校园公告】：\n");
            for (int i = 0; i < Math.min(2, notices.size()); i++) {
                dataContext.append(String.format("- %s (发布时间：%s)\n", notices.get(i).getTitle(), notices.get(i).getCreateTime()));
            }
        }

        // 3. 构建系统提示词 (System Prompt)
        String systemPrompt = "你是一个智慧宿舍管理系统的智能助手，名字叫'宿管AI'。\n" +
                "请基于以下【实时数据库快照】来回答用户的问题。如果数据中包含用户想知道的答案（如电费、报修进度），请直接告诉他具体数字或状态。\n" +
                "不要总是说客套话，要解决实际问题。\n\n" +
                "=== 实时数据库快照 ===\n" +
                dataContext.toString() +
                "\n====================\n\n" +
                "回答要求：\n" +
                "1. 如果用户问电费，直接回答余额数字。\n" +
                "2. 如果用户问报修，直接回答最近的单子修好没。\n" +
                "3. 语气亲切、简洁，使用 Markdown 格式（分点、加粗）。";

        // 4. 打印日志看看我们要发给AI什么数据 (调试用)
        System.out.println("----- 发送给AI的Prompt -----\n" + systemPrompt);

        // 5. 异步调用 DeepSeek
        executor.execute(() -> {
            try {
                String jsonBody = String.format(
                        "{" +
                                "  \"model\": \"deepseek-chat\"," +
                                "  \"messages\": [" +
                                "    {\"role\": \"system\", \"content\": \"%s\"}," +
                                "    {\"role\": \"user\", \"content\": \"%s\"}" +
                                "  ]," +
                                "  \"stream\": true" +
                                "}",
                        escapeJson(systemPrompt),
                        escapeJson(question)
                );

                Request request = new Request.Builder()
                        .url(apiUrl)
                        .addHeader("Authorization", "Bearer " + apiKey)
                        .addHeader("Content-Type", "application/json")
                        .post(RequestBody.create(jsonBody, MediaType.parse("application/json")))
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    if (!response.isSuccessful()) {
                        emitter.send(SseEmitter.event().data("AI 服务异常: " + response.code()));
                        emitter.complete();
                        return;
                    }
                    BufferedReader reader = new BufferedReader(new InputStreamReader(response.body().byteStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (line.startsWith("data: ")) {
                            String data = line.substring(6).trim();
                            if ("[DONE]".equals(data)) break;
                            try {
                                JsonNode root = objectMapper.readTree(data);
                                JsonNode choices = root.path("choices");
                                if (!choices.isMissingNode() && choices.size() > 0) {
                                    JsonNode delta = choices.get(0).path("delta");
                                    if (delta.has("content")) {
                                        emitter.send(SseEmitter.event().data(delta.get("content").asText()));
                                    }
                                }
                            } catch (Exception e) {}
                        }
                    }
                    emitter.complete();
                }
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        });

        return emitter;
    }

    private String getUserRoleName(int type) {
        return switch (type) {
            case 0 -> "学生";
            case 1 -> "辅导员";
            case 2 -> "宿管";
            case 3 -> "管理员";
            default -> "用户";
        };
    }

    private String escapeJson(String text) {
        if (text == null) return "";
        return text.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r");
    }
}
package com.ylb.service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface AiService {
    SseEmitter streamChat(String question); // 新增流式接口
}
package com.ylb.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 提交请假申请请求参数（镜像 TbLeaveApply 实体字段，保证前端 JSON 原样绑定）
 */
@Data
public class LeaveApplyDTO {
    private Long applyId;
    private Long studentId;
    private Long counselorId; // 审批人

    @NotBlank(message = "请假类型不能为空")
    private String type;      // 事假/病假

    @NotNull(message = "开始时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime startTime;

    @NotNull(message = "结束时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime endTime;

    @NotBlank(message = "请假事由不能为空")
    private String reason;

    private Integer status;   // 0-待审批, 1-已批准, 2-已驳回
    private String auditRemark;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime auditTime;
}

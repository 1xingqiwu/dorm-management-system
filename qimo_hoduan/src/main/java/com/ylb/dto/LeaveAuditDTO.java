package com.ylb.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 辅导员审批请假请求参数（镜像 TbLeaveApply 实体字段，保证前端 JSON 原样绑定）
 */
@Data
public class LeaveAuditDTO {
    @NotNull(message = "申请ID不能为空")
    private Long applyId;

    private Long studentId;
    private Long counselorId;
    private String type;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime endTime;

    private String reason;

    @NotNull(message = "审批状态不能为空")
    private Integer status;   // 0-待审批, 1-已批准, 2-已驳回

    private String auditRemark;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime auditTime;
}

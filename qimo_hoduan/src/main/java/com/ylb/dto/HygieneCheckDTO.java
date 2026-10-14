package com.ylb.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 录入卫生检查请求参数（镜像 TbHygieneCheck 实体字段，保证前端 JSON 原样绑定）
 */
@Data
public class HygieneCheckDTO {
    private Long checkId;

    @NotNull(message = "宿舍ID不能为空")
    private Long roomId;

    @NotNull(message = "分数不能为空")
    @Min(value = 0, message = "分数不能小于0")
    @Max(value = 100, message = "分数不能大于100")
    private Integer score;       // 分数

    private String deductReason; // 扣分原因

    // 注意：检查日期通常只需要年月日
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate checkDate;

    private String checkerName;  // 检查人

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    private String roomName;
}

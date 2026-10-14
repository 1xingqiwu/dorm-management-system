package com.ylb.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 提交报修请求参数（镜像 TbRepair 实体字段，保证前端 JSON 原样绑定）
 */
@Data
public class RepairSubmitDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long repairId;
    private Long studentId;       // 报修人
    private Long buildingId;
    private Long roomId;

    @NotBlank(message = "报修类型不能为空")
    private String repairType;    // 水电/家具

    @NotBlank(message = "故障描述不能为空")
    private String description;   // 描述

    private String imageUrls;     // 图片URL，逗号分隔

    private Integer status;       // 0-待审核, 1-已派单, 2-维修中, 3-已完成

    private String repairerName;
    private String repairerPhone;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime submitTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime finishTime;

    private Integer rateScore;    // 评分
    private String rateComment;   // 评价
}

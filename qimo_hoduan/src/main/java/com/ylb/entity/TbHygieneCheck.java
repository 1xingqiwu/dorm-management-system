package com.ylb.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TbHygieneCheck {
    private Long checkId;
    private Long roomId;
    private Integer score;       // 分数
    private String deductReason; // 扣分原因
    
    // 注意：检查日期通常只需要年月日
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate checkDate;
    
    private String checkerName;  // 检查人
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    // 扩展字段：用于列表展示宿舍名
    private String roomName;
}
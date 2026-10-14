package com.ylb.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class TbRepair implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long repairId;
    private Long studentId;       // 报修人
    private Long buildingId;
    private Long roomId;
    private String repairType;    // 水电/家具
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
package com.ylb.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TbVisitor {
    private Long visitorId;
    private Long studentId;      // 被访学生ID
    private String visitorName;  // 访客姓名
    private String idCard;       // 身份证
    private String phone;        // 手机号
    private String reason;       // 事由
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime visitStartTime; // 预约来访时间
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime visitEndTime;   // 实际离开时间
    
    private Integer status;      // 0-待审核, 1-待来访, 2-已进入, 3-已离开, 4-已驳回
    private String qrCode;       // 通行码(预留)
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
}
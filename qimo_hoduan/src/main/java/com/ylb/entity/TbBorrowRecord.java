package com.ylb.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TbBorrowRecord {
    private Long recordId;
    private Long studentId;
    private Long itemId;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime borrowTime;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime returnTime;
    
    private Integer status; // 0-借用中, 1-已归还
    
    // 扩展字段：用于前端显示
    private String studentName; // 借用人名字
    private String itemName;    // 物品名字
    private String studentNo;   // 借用人学号
    private String className;   // 借用人班级
}
package com.ylb.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SysNotice {
    private Long noticeId;
    private String title;
    private String content;
    private Long publisherId;
    private Integer targetType; // 0-全员
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;
    
    private Integer isDeleted;

    // 业务扩展字段（数据库表中没有，但在查询时关联出来）
    private String publisherName; 
}
package com.ylb.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 发布公告请求参数（镜像 SysNotice 实体字段，保证前端 JSON 原样绑定）
 */
@Data
public class NoticeAddDTO {
    private Long noticeId;

    @NotBlank(message = "公告标题不能为空")
    private String title;

    @NotBlank(message = "公告内容不能为空")
    private String content;

    private Long publisherId;
    private Integer targetType; // 0-全员

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    private Integer isDeleted;

    private String publisherName;
}

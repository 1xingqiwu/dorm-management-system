package com.ylb.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统用户表实体
 */
@Data
public class SysUser implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long userId;        // 主键
    private String username;    // 账号
    private String password;    // 密码
    private Integer userType;   // 身份: 0-学生, 1-辅导员, 2-宿管
    private String nickname;    // 昵称
    private String avatar;      // 头像
    private String phone;
    private String email;
    private Integer status;     // 1-正常
    private String loginIp;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime loginDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;
    private Integer isDeleted;
}
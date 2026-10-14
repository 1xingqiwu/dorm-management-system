package com.ylb.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

/**
 * 新增账号请求参数
 * 用于超级管理员在「账号管理」中创建学生/辅导员/宿管账号
 */
@Data
public class CreateUserDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 登录账号(学号/工号) */
    @NotBlank(message = "账号不能为空")
    @Pattern(regexp = "^[A-Za-z0-9_]{3,20}$", message = "账号需为3-20位字母、数字或下划线")
    private String username;

    /** 姓名/显示名 */
    @NotBlank(message = "姓名不能为空")
    @Size(max = 64, message = "姓名长度不能超过64位")
    private String nickname;

    /** 角色: 0-学生, 1-辅导员, 2-宿管 (不允许创建管理员) */
    @NotNull(message = "角色不能为空")
    private Integer userType;

    /** 登录密码(明文，入库前加密) */
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 32, message = "密码长度需为6-32位")
    private String password;

    /** 手机号 */
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;

    /** 邮箱(可选，用于找回密码) */
    private String email;
}

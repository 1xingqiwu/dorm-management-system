package com.ylb.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginDTO {
    @NotBlank(message = "账号不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

    // 0-学生, 1-辅导员, 2-宿管
    @NotNull(message = "请选择登录身份")
    private Integer userType;
}

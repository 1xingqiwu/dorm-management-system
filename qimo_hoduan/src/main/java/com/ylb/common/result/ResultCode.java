package com.ylb.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {

    SUCCESS(200, "操作成功"),
    ERROR(500, "系统内部异常"),
    
    // 认证授权相关
    UNAUTHORIZED(401, "未授权，请登录"),
    FORBIDDEN(403, "权限不足，无法访问"),
    TOKEN_INVALID(401, "Token无效或已过期"),
    
    // 业务相关
    BUSINESS_ERROR(400, "业务处理失败"),
    USER_NOT_EXIST(1001, "用户不存在"),
    PASSWORD_ERROR(1002, "用户名或密码错误"),
    PARAM_ERROR(1003, "参数错误");

    private final Integer code;
    private final String msg;
}
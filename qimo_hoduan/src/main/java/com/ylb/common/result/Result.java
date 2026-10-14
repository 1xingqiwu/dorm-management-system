package com.ylb.common.result;

import lombok.Data;
import java.io.Serializable;

/**
 * 统一API响应结果封装
 * @param <T> 数据类型
 */
@Data
public class Result<T> implements Serializable {

    private Integer code; // 状态码
    private String msg;   // 提示信息
    private T data;       // 数据载荷

    // 私有构造，强制使用静态方法创建
    private Result() {}

    // 成功 - 无数据
    public static <T> Result<T> success() {
        return build(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), null);
    }

    // 成功 - 带数据
    public static <T> Result<T> success(T data) {
        return build(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), data);
    }

    // 失败 - 使用默认错误码
    public static <T> Result<T> error() {
        return build(ResultCode.ERROR.getCode(), ResultCode.ERROR.getMsg(), null);
    }

    // 失败 - 自定义错误信息
    public static <T> Result<T> error(String msg) {
        return build(ResultCode.ERROR.getCode(), msg, null);
    }

    // 失败 - 自定义状态码和信息
    public static <T> Result<T> error(Integer code, String msg) {
        return build(code, msg, null);
    }

    // 构建方法
    private static <T> Result<T> build(Integer code, String msg, T data) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }
}
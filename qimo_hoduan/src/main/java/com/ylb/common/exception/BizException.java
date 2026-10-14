package com.ylb.common.exception;

import com.ylb.common.result.ResultCode;
import lombok.Getter;

/**
 * 业务异常：用于「可安全展示给用户」的业务错误提示。
 * 与系统异常区分——系统异常(如 SQL/空指针)不会把内部信息返回给前端。
 */
@Getter
public class BizException extends RuntimeException {

    /** 业务错误码 */
    private final Integer code;

    public BizException(String message) {
        super(message);
        this.code = ResultCode.BUSINESS_ERROR.getCode();
    }

    public BizException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}

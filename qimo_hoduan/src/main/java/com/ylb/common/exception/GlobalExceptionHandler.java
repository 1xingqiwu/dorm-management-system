package com.ylb.common.exception;


import com.ylb.common.result.Result;
import com.ylb.common.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 * 拦截Controller层抛出的异常，转为Result对象返回
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 权限不足异常
     */
    @ExceptionHandler(AccessDeniedException.class)
    public Result<?> handleAccessDeniedException(AccessDeniedException e) {
        log.warn("权限不足: {}", e.getMessage());
        return Result.error(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMsg());
    }

    /**
     * 登录认证异常 (密码错误/用户不存在)
     */
    @ExceptionHandler({BadCredentialsException.class, InternalAuthenticationServiceException.class})
    public Result<?> handleAuthenticationException(Exception e) {
        log.warn("认证失败: {}", e.getMessage());
        return Result.error(ResultCode.PASSWORD_ERROR.getCode(), "账号或密码错误");
    }

    /**
     * 参数校验异常 (@Valid 校验失败)
     * 返回第一个字段的校验提示，便于前端展示
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> handleValidationException(MethodArgumentNotValidException e) {
        String msg = e.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(FieldError::getDefaultMessage)
                .orElse(ResultCode.PARAM_ERROR.getMsg());
        log.warn("参数校验失败: {}", msg);
        return Result.error(ResultCode.PARAM_ERROR.getCode(), msg);
    }

    /**
     * 业务异常：返回可安全展示给用户的业务文案(错误码 + 消息)
     */
    @ExceptionHandler(BizException.class)
    public Result<?> handleBizException(BizException e) {
        log.warn("业务异常: {}", e.getMessage());
        return Result.error(e.getCode(), e.getMessage());
    }

    /**
     * 兜底运行时异常：不向前端暴露内部信息(如 SQL/空指针)，详情只记日志
     */
    @ExceptionHandler(RuntimeException.class)
    public Result<?> handleRuntimeException(RuntimeException e) {
        log.error("系统异常", e);
        return Result.error(ResultCode.ERROR.getCode(), ResultCode.ERROR.getMsg());
    }

    /**
     * 系统兜底异常
     */
    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception e) {
        log.error("系统内部异常", e);
        return Result.error(ResultCode.ERROR.getCode(), "系统繁忙，请稍后再试");
    }
}
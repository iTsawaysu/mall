package com.sun.mall.product.exception;

import com.sun.mall.common.exception.BizCodeEnum;
import com.sun.mall.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sun
 */
@RestControllerAdvice(basePackages = "com.sun.mall.product.controller")
@Slf4j
public class MallExceptionControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R handleDataVerificationException(MethodArgumentNotValidException e) {
        log.error("异常类型：{}；异常信息：{}", e.getClass(), e.getMessage());
        Map<String, String> map = new HashMap<>();
        BindingResult bindingResult = e.getBindingResult();
        bindingResult.getFieldErrors().forEach(fieldError -> map.put(fieldError.getField(), fieldError.getDefaultMessage()));
        return R.error(BizCodeEnum.VALID_EXCEPTION.getCode(), BizCodeEnum.VALID_EXCEPTION.getMsg()).put("data", map);
    }

    @ExceptionHandler(Throwable.class)
    public R handleGlobalException(Throwable throwable) {
        log.error("异常类型：{}；异常信息：{}", throwable.getClass(), throwable.getMessage());
        return R.error(BizCodeEnum.UNKNOWN_EXCEPTION.getCode(), BizCodeEnum.UNKNOWN_EXCEPTION.getMsg());
    }
}

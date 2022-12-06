package com.xws.bootpro.utils;

import com.xws.bootpro.dataobj.Enum.StatusCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 *@Author：lm
 *@Date：2022/8/9 11:37
 *@Description 全局异常捕获处理
*/

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 捕获其他异常
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ApiResult<String> handle(Exception e) {
        log.error("全局异常信息：{}", e.getMessage());
        return ApiResult.fail(null,StatusCodeEnum.SC500.getCode(), StatusCodeEnum.SC500.getMsg() + "：" + e.getMessage(),e.getMessage());
    }

    /**
     * 捕获自定义异常
     */
    @ExceptionHandler(CustomException.class)
    public ApiResult<String> handle(CustomException e) {
        log.error("自定义异常信息：{}", e.getMessage());
        return ApiResult.fail(null,e.getCode(), e.getMessage(),null);
    }

}


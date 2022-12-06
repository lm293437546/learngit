package com.xws.bootpro.utils;

import com.xws.bootpro.dataobj.Enum.StatusCodeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 *@Author：lm
 *@Date：2022/8/9 11:36
 *@Description 封装返回结果
*/

@Data
public class ApiResult<T> implements Serializable {
    private String cause;
    private String code;
    private T data;
    private String message;
    private String stack;


    public static <T> ApiResult<T> success(T data) {
        return ApiResult.success(StatusCodeEnum.SC200.getMsg(), data);
    }

    public static <T> ApiResult<T> success(String msg, T data) {
        ApiResult<T> apiResult = new ApiResult<>();
        apiResult.setCause("");
        apiResult.setCode(StatusCodeEnum.SC200.getCode());
        apiResult.setData(data);
        apiResult.setMessage(msg);
        apiResult.setStack("");
        return apiResult;
    }

    public static <T> ApiResult<T> fail(String cause,String code, String message,String Stack) {
        ApiResult<T> apiResult = new ApiResult<>();
        apiResult.setCause(cause);
        apiResult.setCode(code);
        apiResult.setMessage(message);
        apiResult.setStack(Stack);
        return apiResult;
    }
}

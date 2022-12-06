package com.xws.bootpro.utils;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *@Author：lm
 *@Date：2022/8/9 15:24
 *@Description 自定义异常实体
*/

@Data
// lombok提供的有参构造
@AllArgsConstructor
// lombok提供的无参构造
@NoArgsConstructor
public class CustomException extends RuntimeException {

    @ApiModelProperty("状态码")
    private String code;
    @ApiModelProperty("异常信息")
    private String message;

}

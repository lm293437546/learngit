package com.xws.bootpro.dataobj.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *@Author：lm
 *@Date：2022/8/9 10:04
 *@Description 用户登录请求类
*/

@Setter
@Getter
@ToString
public class LoginRequestDto {

    /**
     * 用户code
     */
    private String userCode;

    /**
     * 用户密码
     */
    private String password;

}

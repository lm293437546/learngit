package com.xws.bootpro.dataobj.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *@Author：lm
 *@Date：2022/8/9 10:04
 *@Description 修改密码请求类
*/

@Setter
@Getter
@ToString
public class ModifyPWDRequestDto {

    /**
     * 用户code
     */
    private String userCode;

    /**
     * 旧密码
     */
    private String oldPwd;

    /**
     * 新密码
     */
    private String newPwd;

}

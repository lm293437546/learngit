package com.xws.bootpro.dataobj.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *@Author：lm
 *@Date：2022/8/9 14:43
 *@Description 公共服务错误码枚举类
*/

@AllArgsConstructor
@Getter
public enum StatusCodeEnum {
    SC200("0", "操作成功"),
    SC999("999", "操作失败"),
    SC100("100","用户名或者密码错误"),
    SC101("101","用户已失效，请重新登录"),
    SC102("102","旧密码错误，请重新输入"),
    SC103("103","该用户名已被使用,请重新输入用户名"),
    SC104("104","删除失败,需要先删除该菜单的子级菜单"),
    SC105("105","该商品类型编码已被使用,请重新输入商品类型编码"),
    SC106("106","无效访问"),
    SC107("107","该用户已锁定，请稍后再试"),
    SC500("500", "系统异常，请稍后重试"),

    DEFAULT_PASSWORD("DEFAULT.PASSWORD","默认密码"),

    USER_LOCK_NUMBER("USER.LOCK.NUMBER","用户输错密码次数将锁定"),
    USER_UNLOCK_DATE("USER.UNLOCK.DATE","自动解锁时间：分钟"),

    RSA_PUBLICKEY("RSA.PUBLICKEY","RSA加密公钥"),
    RSA_PRIVATEKEY("RSA.PRIVATEKEY","RSA加密私钥"),

    SESSION_MAX_INTERVAL("SESSION.MAX.INTERVAL","session有效时间(单位：分钟)");

    private final String code;
    private final String msg;
}



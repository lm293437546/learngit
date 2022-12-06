package com.xws.bootpro.utils;

import javax.servlet.http.Cookie;
import java.util.Random;

/**
 *@Author：lm
 *@Date：2022/8/9 15:44
 *@Description 常用的工具类
*/

public class CommonUtil {

    /**
     *
     * 根据key获取cookie的值
     *
     **/
    public static String getCookieValue(Cookie[] cookies,String key) throws Exception{
        String value = null;
        if(null != cookies && cookies.length > 0){
            for (Cookie cookie: cookies){
                if(cookie.getName().equals(key)){
                    value = cookie.getValue();
                    break;
                }
            }
        }
        return value;
    }

    /**
     *随机生成length长度的随机数
     */
    public static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

}

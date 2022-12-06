package com.xws.bootpro.dataobj.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *@Author：lm
 *@Date：2022/8/9 10:04
 *@Description 获取菜单类
*/

@Setter
@Getter
@ToString
public class LogListDto {

    private String usercode;

    private String username;

    private String ip;

    private String addr;

    private String logtime;

    private String reserve1;

    private String reserve2;

    private String reserve3;

}

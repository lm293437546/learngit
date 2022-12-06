package com.xws.bootpro.dataobj.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *@Author：lm
 *@Date：2022/8/16 15:01
 *@Description 商品浏览记录类
*/

@Setter
@Getter
@ToString
public class WaresActionDto {

    private String userip;

    private String useraddr;

    private String warescode;

    private String waresname;

    private String actiontime;

    private String reserve1;

    private String reserve2;

    private String reserve3;

}

package com.xws.bootpro.dataobj.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *@Author：lm
 *@Date：2022/8/16 15:00
 *@Description 商品成交类
*/

@Setter
@Getter
@ToString
public class EditBuysRequestDto {

    /**
     * 用户code
     */
    private String userCode;

    private String buyno;

    private String buyer;

    private String warescode;

    private String buytime;

    private String gmv;

    private String reserve1;

    private String reserve2;

    private String reserve3;

    private String type;  //add:新增，update：修改

}

package com.xws.bootpro.dataobj.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 *@Author：lm
 *@Date：2022/8/16 15:00
 *@Description 商品类型类
*/

@Setter
@Getter
@ToString
public class EditWaresTypeRequestDto {

    /**
     * 用户code
     */
    private String userCode;

    private String warescode;

    private String waresname;

    private String reserve1;

    private String reserve2;

    private String reserve3;

    private String type;  //add:新增，update：修改

}

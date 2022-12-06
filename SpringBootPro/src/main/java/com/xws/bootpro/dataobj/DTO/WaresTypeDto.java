package com.xws.bootpro.dataobj.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *@Author：lm
 *@Date：2022/8/16 15:01
 *@Description 商品类型类
*/

@Setter
@Getter
@ToString
public class WaresTypeDto {

    private String warescode;

    private String waresname;

    private String reserve1;

    private String reserve2;

    private String reserve3;

}

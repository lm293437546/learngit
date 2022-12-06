package com.xws.bootpro.dataobj.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 *@Author：lm
 *@Date：2022/8/16 15:01
 *@Description 成交记录记录类
*/

@Setter
@Getter
@ToString
public class BuysDto {

    private String buyno;

    private String buyer;

    private String warescode;

    private String waresname;

    private String buytime;

    private Integer gmv;

    private String reserve1;

    private String reserve2;

    private String reserve3;

    private List<WaresTypeDto> types;

}

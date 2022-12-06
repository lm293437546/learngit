package com.xws.bootpro.dataobj.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *@Author：lm
 *@Date：2022/9/13 10:30
 *@Description 首页统计（收益数据）
*/

@Setter
@Getter
@ToString
public class HomeCount3Dto {

    //总收益
    private Integer revenueTotal;

    //当月收益
    private Integer revenueMonth;

    //当日收益
    private Integer revenueDay;

    //总成交量
    private Integer dealTotal;

    //当月成交量
    private Integer dealMonth;

    //当日成交量
    private Integer dealDay;

}

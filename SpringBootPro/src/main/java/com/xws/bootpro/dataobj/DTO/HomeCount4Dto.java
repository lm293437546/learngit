package com.xws.bootpro.dataobj.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *@Author：lm
 *@Date：2022/9/13 10:30
 *@Description 首页统计（商品信息）
*/

@Setter
@Getter
@ToString
public class HomeCount4Dto {

    //商品编号
    private String bh;

    //总成交
    private Integer deal;

    //推荐成交
    private Integer recommend;

    //单价
    private Integer price;

    //总收入
    private Integer income;

}

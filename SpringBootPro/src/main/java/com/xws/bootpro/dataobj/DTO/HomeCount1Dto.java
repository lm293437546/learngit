package com.xws.bootpro.dataobj.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *@Author：lm
 *@Date：2022/9/13 10:30
 *@Description 首页统计（总数）
*/

@Setter
@Getter
@ToString
public class HomeCount1Dto {

    //用户数量
    private Integer userCount;

    //商品数量
    private Integer waresCount;

    //用户浏览商品次数
    private Integer browseCount;

    //推荐成交次数
    private Integer recommendCount;

}

package com.xws.bootpro.dataobj.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *@Author：lm
 *@Date：2022/9/13 10:30
 *@Description 首页统计（各商品浏览次数）
*/

@Setter
@Getter
@ToString
public class HomeCount2Dto {

    //商品编号
    private String waresType;

    //商品浏览次数
    private Integer waresCount;

}

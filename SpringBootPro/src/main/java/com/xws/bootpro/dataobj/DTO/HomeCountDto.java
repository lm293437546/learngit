package com.xws.bootpro.dataobj.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 *@Author：lm
 *@Date：2022/9/13 10:30
 *@Description 首页统计
*/

@Setter
@Getter
@ToString
public class HomeCountDto {

    //总数
    private HomeCount1Dto homeCount1Dto;

    //各商品浏览次数
    private List<HomeCount2Dto> homeCount2Dtos;

    //收益数据
    private HomeCount3Dto homeCount3Dto;

    //商品信息
    private List<HomeCount4Dto> homeCount4Dtos;

}

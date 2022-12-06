package com.xws.bootpro.dataobj.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 *@Author：lm
 *@Date：2022/8/16 15:00
 *@Description 查询类
*/

@Setter
@Getter
@ToString
public class SelectRequestDto {

    /**
     * 用户code
     */
    private String userCode;

    /**
     * 关键字
     */
    private String keyword;

    private String userId;

    private String id;

    private List<String> nodes;

}

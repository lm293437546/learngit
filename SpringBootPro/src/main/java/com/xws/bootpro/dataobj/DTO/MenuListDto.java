package com.xws.bootpro.dataobj.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedList;
import java.util.List;

/**
 *@Author：lm
 *@Date：2022/8/9 10:04
 *@Description 获取菜单类
*/

@Setter
@Getter
@ToString
public class MenuListDto {

    private Integer menuid;
    private Integer parentid;
    private String menuname;
    private String addr;
    private Integer sort;
    private String icon;
    private String createtime;
    private String remark;
    private String reserve;
    private List<MenuListDto> children = new LinkedList<>();

}

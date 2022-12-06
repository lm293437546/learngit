package com.xws.bootpro.dataobj.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *@Author：lm
 *@Date：2022/8/16 15:00
 *@Description 编辑菜单类
*/

@Setter
@Getter
@ToString
public class EditMenuRequestDto {

    /**
     * 用户code
     */
    private String userCode;

    private Integer menuId;

    private Integer parentId;

    private String menuName;

    private String addr;

    private Integer sort;

    private String icon;

    private String remark;

    private String type;  //add:新增，update：修改

}

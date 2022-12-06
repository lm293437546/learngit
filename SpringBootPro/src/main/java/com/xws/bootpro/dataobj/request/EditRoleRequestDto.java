package com.xws.bootpro.dataobj.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *@Author：lm
 *@Date：2022/8/16 15:00
 *@Description 编辑角色类
*/

@Setter
@Getter
@ToString
public class EditRoleRequestDto {

    /**
     * 用户code
     */
    private String userCode;

    private Integer roleId;

    private String roleName;

    private String remark;

    private String type;  //add:新增，update：修改

}

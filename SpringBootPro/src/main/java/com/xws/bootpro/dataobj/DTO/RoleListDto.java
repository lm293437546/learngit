package com.xws.bootpro.dataobj.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *@Author：lm
 *@Date：2022/8/16 15:01
 *@Description 角色类
*/

@Setter
@Getter
@ToString
public class RoleListDto {

    private Integer roleid;

    private String rolename;

    private String createtime;

    private String remark;


}

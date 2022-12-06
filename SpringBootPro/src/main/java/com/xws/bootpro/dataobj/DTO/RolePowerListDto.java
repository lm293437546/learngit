package com.xws.bootpro.dataobj.DTO;

import com.xws.bootpro.dataobj.PaMenuRoleDo;
import com.xws.bootpro.dataobj.PaRoleDo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedList;
import java.util.List;

/**
 *@Author：lm
 *@Date：2022/8/16 15:01
 *@Description
*/

@Setter
@Getter
@ToString
public class RolePowerListDto {

    //所有权限
    private List<MenuListDto> menuList = new LinkedList<>();

    //角色拥有的权限
    private List<PaMenuRoleDo> menuRoleList = new LinkedList<>();

}

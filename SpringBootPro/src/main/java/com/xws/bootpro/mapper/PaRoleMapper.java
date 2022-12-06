package com.xws.bootpro.mapper;

import com.xws.bootpro.dataobj.DTO.MenuListDto;
import com.xws.bootpro.dataobj.DTO.RoleListDto;
import com.xws.bootpro.dataobj.PaRoleDo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PaRoleMapper extends Mapper<PaRoleDo> {

    @Select("select a.roleid,a.rolename,a.createtime,a.remark from pa_role a " +
            "where a.rolename like #{keyword} order by a.createtime")
    List<RoleListDto> selectRoleList(@Param("keyword") String keyword);

    @Select("select max(roleid) from pa_role")
    int getMaxId();

    @Select("select a.menuid,a.parentid,a.menuname,a.addr,a.sort,a.icon,a.createtime,a.remark,a.reserve " +
            "from pa_menu a " +
            "left join pa_menu_role b on a.menuid = b.menuid " +
            "left join pa_user_role c on b.roleid = c.roleid " +
            "order by a.sort")
    List<MenuListDto> getAllMenu();

}

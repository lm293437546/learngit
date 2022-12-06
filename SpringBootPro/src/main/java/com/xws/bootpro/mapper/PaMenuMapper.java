package com.xws.bootpro.mapper;

import com.xws.bootpro.dataobj.DTO.MenuListDto;
import com.xws.bootpro.dataobj.PaMenuDo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PaMenuMapper extends Mapper<PaMenuDo> {

    @Select("select a.menuid,a.parentid,a.menuname,a.addr,a.sort,a.icon,a.createtime,a.remark,a.reserve " +
            "from pa_menu a " +
            "left join pa_menu_role b on a.menuid = b.menuid " +
            "left join pa_user_role c on b.roleid = c.roleid " +
            "where c.userCode = #{userCode} order by a.sort")
    List<MenuListDto> getMenus(@Param("userCode") String userCode);

    //获取列表
    @Select("select a.menuid,a.parentid,a.menuname,a.addr,a.sort,a.icon,a.createtime,a.remark,a.reserve " +
            "from pa_menu a where a.menuname like #{keyword} order by a.createtime")
    List<MenuListDto> selectMenuList(@Param("keyword") String keyword);

    //获取详情
    @Select("select a.menuid,a.parentid,a.menuname,a.addr,a.sort,a.icon,a.createtime,a.remark,a.reserve " +
            "from pa_menu a where a.menuid = #{menuid} ")
    MenuListDto selectinfo(@Param("menuid") String menuid);

    //获取第一、二级菜单
    @Select("select a.menuid,a.parentid,a.menuname,a.addr,a.sort,a.icon,a.createtime,a.remark,a.reserve " +
            "from pa_menu a where a.remark = '顶级菜单' or a.remark = '一级菜单' order by a.parentid,a.sort")
    List<MenuListDto> getFSmenus();

    //获取menuid最大值
    @Select("select max(menuid) from pa_menu")
    int getMaxId();

    //获取最大排序号
    @Select("select max(sort) from pa_menu where parentId = #{parentId} and remark = #{remark}")
    String getMaxSort(@Param("remark") String remark, @Param("parentId") Integer parentId);

}

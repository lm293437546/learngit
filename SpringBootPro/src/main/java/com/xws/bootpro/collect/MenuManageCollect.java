package com.xws.bootpro.collect;

import com.xws.bootpro.dataobj.DTO.MenuListDto;
import com.xws.bootpro.dataobj.Enum.StatusCodeEnum;
import com.xws.bootpro.dataobj.PaMenuDo;
import com.xws.bootpro.dataobj.PaMenuRoleDo;
import com.xws.bootpro.dataobj.request.EditMenuRequestDto;
import com.xws.bootpro.dataobj.request.SelectRequestDto;
import com.xws.bootpro.mapper.PaMenuMapper;
import com.xws.bootpro.mapper.PaMenuRoleMapper;
import com.xws.bootpro.mapper.PaRoleMapper;
import com.xws.bootpro.utils.CustomException;
import com.xws.bootpro.utils.DateUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

/**
 *@Author：lm
 *@Date：2022/8/18 14:42
 *@Description 菜单管理类
*/

@RestController
@RequestMapping("api/menuManage")
public class MenuManageCollect {

    @Resource
    PaMenuMapper paMenuMapper;

    @Resource
    PaMenuRoleMapper paMenuRoleMapper;

    @Resource
    PaRoleMapper paRoleMapper;

    /**
     * 查询列表
     */
    @RequestMapping("selectMenuList")
    public List<MenuListDto> selectMenuList(@RequestBody SelectRequestDto request) throws Exception{
        List<MenuListDto> configMenuList = new LinkedList<>();
        Map<Integer, MenuListDto> menuRefsMap = new LinkedHashMap<>(128);
        List<MenuListDto> mens = paRoleMapper.getAllMenu();
        for (MenuListDto menu : mens){
            menuRefsMap.put(menu.getMenuid(), menu);
        }
        //处理上下级
        menuRefsMap.entrySet().forEach(entry -> {
            if(entry.getValue().getParentid() == 0){
                configMenuList.add(entry.getValue());
            }else{
                MenuListDto pUserMenu = menuRefsMap.get(entry.getValue().getParentid());
                if(pUserMenu != null){
                    pUserMenu.getChildren().add(entry.getValue());
                }
            }
        });
        return configMenuList;
    }

    /**
     * 编辑菜单
     */
    @RequestMapping("editMenu")
    public void editMenu(@RequestBody EditMenuRequestDto request) throws Exception{
        PaMenuDo paMenuDo = new PaMenuDo();
        paMenuDo.setParentid(request.getParentId());
        paMenuDo.setMenuname(request.getMenuName());
        paMenuDo.setAddr(request.getAddr());

        if(request.getType().equals("add")){
            //新增
            PaMenuDo pmen = paMenuMapper.selectByPrimaryKey(request.getParentId());
            if(pmen.getRemark().equals("顶级菜单")){
                paMenuDo.setRemark("一级菜单");
            }else if(pmen.getRemark().equals("一级菜单")){
                paMenuDo.setRemark("二级菜单");
            }
            int maxid = paMenuMapper.getMaxId();
            paMenuDo.setMenuid(maxid +1);
            //获取最大排序
            String maxsort = paMenuMapper.getMaxSort(paMenuDo.getRemark(),request.getParentId());
            if(null == maxsort){
                paMenuDo.setSort(1);
            }else{
                paMenuDo.setSort(Integer.parseInt(maxsort) + 1);
            }

            paMenuDo.setCreatetime(DateUtil.dateToTime(new Date(),"yyyy-MM-dd HH:mm:ss"));
            paMenuDo.setIcon("");
            paMenuMapper.insert(paMenuDo);
        }else{
            //修改
            paMenuDo.setMenuid(request.getMenuId());
            paMenuMapper.updateByPrimaryKeySelective(paMenuDo);
        }
    }

    /**
     *删除菜单
     */
    @RequestMapping("deleteMenu")
    public void deleteMenu(@RequestBody EditMenuRequestDto request) throws Exception{
        PaMenuDo paMenuDo = new PaMenuDo();
        paMenuDo.setParentid(request.getMenuId());
        List<PaMenuDo> menuDoList = paMenuMapper.select(paMenuDo);
        if(null == menuDoList || menuDoList.size() < 1){
            //删除菜单
            paMenuMapper.deleteByPrimaryKey(request.getMenuId());
            //删除菜单-角色
            PaMenuRoleDo paMenuRoleDo = new PaMenuRoleDo();
            paMenuRoleDo.setMenuid(request.getMenuId());
            paMenuRoleMapper.delete(paMenuRoleDo);
        }else{
            throw new CustomException(StatusCodeEnum.SC104.getCode(),StatusCodeEnum.SC104.getMsg());
        }
    }

    /**
     * 菜单详情
     */
    @RequestMapping("menuinfo")
    public MenuListDto menuinfo(@RequestBody SelectRequestDto request) throws Exception{
        MenuListDto list = new MenuListDto();
        list = paMenuMapper.selectinfo(request.getId());
        if(list == null){
            list = new MenuListDto();
        }
        List<MenuListDto> children = paMenuMapper.getFSmenus();
        list.setChildren(children);
        return list;
    }

}

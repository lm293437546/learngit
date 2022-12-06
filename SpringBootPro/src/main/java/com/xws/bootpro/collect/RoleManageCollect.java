package com.xws.bootpro.collect;

import com.xws.bootpro.dataobj.*;
import com.xws.bootpro.dataobj.DTO.MenuListDto;
import com.xws.bootpro.dataobj.DTO.RoleListDto;
import com.xws.bootpro.dataobj.DTO.RolePowerListDto;
import com.xws.bootpro.dataobj.request.EditRoleRequestDto;
import com.xws.bootpro.dataobj.request.SelectRequestDto;
import com.xws.bootpro.mapper.*;
import com.xws.bootpro.utils.CreateHttp.SysContent;
import com.xws.bootpro.utils.DateUtil;
import com.xws.bootpro.utils.export.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

/**
 *@Author：lm
 *@Date：2022/8/18 14:42
 *@Description 角色管理类
*/

@Slf4j
@RestController
@RequestMapping("api/roleManage")
public class RoleManageCollect {

    //本系统的访问路径
    @Value("${csrf.baseurl}")
    private String baseurL;

    @Resource
    PaRoleMapper paRoleMapper;

    @Resource
    PaUserRoleMapper paUserRoleMapper;

    @Resource
    PaMenuRoleMapper paMenuRoleMapper;

    /**
     * 查询列表
     */
    @RequestMapping("selectRoleList")
    public List<RoleListDto> selectRoleList(@RequestBody SelectRequestDto request) throws Exception{
        String keyword = request.getKeyword();
        if(StringUtils.isEmpty(keyword)){
            keyword = "%%";
        }else{
            keyword = "%"+keyword+"%";
        }
        return paRoleMapper.selectRoleList(keyword);
    }

    /**
     * 编辑角色
     */
    @RequestMapping("editRole")
    public void editRole(@RequestBody EditRoleRequestDto request) throws Exception{
        PaRoleDo paRoleDo = new PaRoleDo();
        paRoleDo.setRolename(request.getRoleName());
        paRoleDo.setRemark(request.getRemark());

        if(request.getType().equals("add")){
            //新增
            int maxid = paRoleMapper.getMaxId();
            paRoleDo.setRoleid(maxid + 1);
            paRoleDo.setCreatetime(DateUtil.dateToTime(new Date(),"yyyy-MM-dd HH:mm:ss"));
            paRoleMapper.insert(paRoleDo);
        }else{
            //修改
            paRoleDo.setRoleid(request.getRoleId());
            paRoleMapper.updateByPrimaryKeySelective(paRoleDo);
        }
    }

    /**
     *删除角色
     */
    @RequestMapping("deleteRole")
    public void deleteRole(@RequestBody EditRoleRequestDto request) throws Exception{
        //删除角色
        paRoleMapper.deleteByPrimaryKey(request.getRoleId());

        //删除角色-用户
        PaUserRoleDo paUserRoleDo = new PaUserRoleDo();
        paUserRoleDo.setRoleid(request.getRoleId());
        paUserRoleMapper.delete(paUserRoleDo);

        //删除角色-菜单
        PaMenuRoleDo paMenuRoleDo = new PaMenuRoleDo();
        paMenuRoleDo.setRoleid(request.getRoleId());
        paMenuRoleMapper.delete(paMenuRoleDo);
    }

    /**
     * 角色详情
     */
    @RequestMapping("roleinfo")
    public RoleListDto roleinfo(@RequestBody SelectRequestDto request) throws Exception{
        RoleListDto info = new RoleListDto();
        PaRoleDo paRoleDo = paRoleMapper.selectByPrimaryKey(request.getId());
        if(null != paRoleDo){
            info.setRoleid(paRoleDo.getRoleid());
            info.setRolename(paRoleDo.getRolename());
            info.setCreatetime(DateUtil.dateToStr(paRoleDo.getCreatetime(),"yyyy-MM-dd HH:mm:ss"));
            info.setRemark(paRoleDo.getRemark());
        }
        return info;
    }

    /**
     * 导出商品类型列表
     */
    @RequestMapping("ExportRoleList")
    public void ExportRoleList(@RequestBody SelectRequestDto request) throws Exception{
        String keyword = request.getKeyword();
        if(StringUtils.isEmpty(keyword)){
            keyword = "%%";
        }else{
            keyword = "%"+keyword+"%";
        }
        List<RoleListDto> list = paRoleMapper.selectRoleList(keyword);

        List<String> columnList = Arrays.asList("序号","角色名称","创建时间","备注");

        List<List<String>> dataList = new ArrayList<>();
        if(list.size() > 0){
            for (RoleListDto dto : list){
                List<String> str = new ArrayList<>();
                str.add(dto.getRolename());
                str.add(dto.getCreatetime());
                str.add(dto.getRemark());
                dataList.add(str);
            }
        }

        ExcelUtil.uploadExcelAboutUser(SysContent.getResponse(),columnList,dataList,baseurL);
        log.info("用户：" + request.getUserCode() + " 在" + DateUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss") +" 导出角色信息表");
    }

    /**
     * 角色-菜单权限
     */
    @RequestMapping("power")
    public RolePowerListDto power(@RequestBody SelectRequestDto request) throws Exception{
        RolePowerListDto list = new RolePowerListDto();

        //获取所有权限
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
        //去除菜单子项为空的菜单
        removeEmptyMenu(configMenuList);
        list.setMenuList(configMenuList);

        PaMenuRoleDo paMenuRoleDo = new PaMenuRoleDo();
        paMenuRoleDo.setRoleid(Integer.parseInt(request.getId()));
        List<PaMenuRoleDo> menuRoleList = paMenuRoleMapper.select(paMenuRoleDo);
        list.setMenuRoleList(menuRoleList);

        return list;
    }

    /**
     * 去除菜单子项为空的菜单
     */
    private void removeEmptyMenu(List<MenuListDto> menus){
        Iterator<MenuListDto> menuIterator = menus.iterator();
        while(menuIterator.hasNext()){
            MenuListDto umDTO = menuIterator.next();
            if(!umDTO.getRemark().equals("二级菜单") && umDTO.getChildren().isEmpty() && StringUtils.isBlank(umDTO.getAddr())){
                menuIterator.remove();
            }
            //递归菜单
            if(!CollectionUtils.isEmpty(umDTO.getChildren())){
                removeEmptyMenu(umDTO.getChildren());
            }
        }
    }

    /**
     * 编辑权限
     */
    @RequestMapping("editPower")
    public void editPower(@RequestBody SelectRequestDto request) throws Exception{
        List<String> nodes = request.getNodes();
        //删除原来权限
        PaMenuRoleDo mr = new PaMenuRoleDo();
        mr.setRoleid(Integer.parseInt(request.getId()));
        paMenuRoleMapper.delete(mr);
        //新增权限
        for (String node : nodes){
            PaMenuRoleDo paMenuRoleDo = new PaMenuRoleDo();
            paMenuRoleDo.setRoleid(Integer.parseInt(request.getId()));
            paMenuRoleDo.setMenuid(Integer.parseInt(node));
            paMenuRoleMapper.insert(paMenuRoleDo);
        }
    }


}

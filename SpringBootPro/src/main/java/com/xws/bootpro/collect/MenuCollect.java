package com.xws.bootpro.collect;

import com.xws.bootpro.dataobj.DTO.MenuListDto;
import com.xws.bootpro.dataobj.Enum.StatusCodeEnum;
import com.xws.bootpro.dataobj.PaUserDo;
import com.xws.bootpro.dataobj.PaUserSessionDo;
import com.xws.bootpro.dataobj.request.LoginRequestDto;
import com.xws.bootpro.dataobj.request.ModifyPWDRequestDto;
import com.xws.bootpro.mapper.PaCfgSystemMapper;
import com.xws.bootpro.mapper.PaMenuMapper;
import com.xws.bootpro.mapper.PaUserMapper;
import com.xws.bootpro.mapper.PaUserSessionMapper;
import com.xws.bootpro.utils.CreateHttp.SysContent;
import com.xws.bootpro.utils.CustomException;
import com.xws.bootpro.utils.RSA.RSAUtil;
import com.xws.bootpro.utils.sessionUtil.MySessionContext;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 *@Author：lm
 *@Date：2022/8/9 17:07
 *@Description 菜单操作类
*/

@RestController
@RequestMapping("api/menu")
public class MenuCollect {

    @Resource
    PaMenuMapper paMenuMapper;

    @Resource
    PaUserMapper paUserMapper;

    @Resource
    PaUserSessionMapper paUserSessionMapper;

    @Resource
    PaCfgSystemMapper paCfgSystemMapper;

    /**
     * 首页获取用户名称
     */
    @RequestMapping("getUserName")
    public PaUserDo getUserName(@RequestBody LoginRequestDto request) throws Exception{
        PaUserDo paUserDo = new PaUserDo();
        paUserDo.setUsercode(request.getUserCode());
        paUserDo.setState(0);
        paUserDo = paUserMapper.selectOne(paUserDo);
        if(null == paUserDo){
            throw new CustomException(StatusCodeEnum.SC101.getCode(),StatusCodeEnum.SC101.getMsg());
        }
        PaUserDo pu = new PaUserDo();
        pu.setUsername(paUserDo.getUsername());
        pu.setIschange(paUserDo.getIschange());
        return pu;
    }

    /**
     * 根据userCode获取左侧菜单栏数据
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping("getMenus")
    public List<MenuListDto> getMenus(@RequestBody LoginRequestDto request) throws Exception{
        List<MenuListDto> configMenuList = new LinkedList<>();

        Map<Integer, MenuListDto> menuRefsMap = new LinkedHashMap<>(128);

        List<MenuListDto> list = paMenuMapper.getMenus(request.getUserCode());
        for (MenuListDto menu : list){
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
        return configMenuList;
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
     * 注销
     * @param request
     * @throws Exception
     */
    @RequestMapping("cancellation")
    public void cancellation(@RequestBody LoginRequestDto request) throws Exception{
        //获取sessionid
        PaUserSessionDo userSessionDo = new PaUserSessionDo();
        userSessionDo.setUsercode(request.getUserCode());
        String sessionid = paUserSessionMapper.selectOne(userSessionDo).getSessionid();
        // 通过sessionid获取当前session
        MySessionContext myc= MySessionContext.getInstance();
        HttpSession session = myc.getSession(sessionid);
        //使session失效
        session.invalidate();
    }

    /**
     * 首页个人修改密码
     * @param request
     * @throws Exception
     */
    @RequestMapping("changePwd")
    public void changePwd(@RequestBody ModifyPWDRequestDto request) throws Exception{
        if(StringUtils.isBlank(request.getUserCode()) || StringUtils.isBlank(request.getOldPwd())){
            throw new CustomException(StatusCodeEnum.SC999.getCode(),StatusCodeEnum.SC999.getMsg());
        }
        boolean flag = false;
        PaUserDo paUserDo = new PaUserDo();
        paUserDo.setUsercode(request.getUserCode());
        paUserDo.setState(0);
        paUserDo = paUserMapper.selectOne(paUserDo);
        if(null == paUserDo){
            flag = true;
        }else{
            //获取私钥
            String privateKey = paCfgSystemMapper.selectByPrimaryKey(StatusCodeEnum.RSA_PRIVATEKEY.getCode()).getFldval();
            //解密
            String decrypt = RSAUtil.decrypt(paUserDo.getPassword(), privateKey);
            if(!decrypt.equals(request.getUserCode() + request.getOldPwd())){
                flag = true;
            }
        }
        if(flag){
            throw new CustomException(StatusCodeEnum.SC999.getCode(),StatusCodeEnum.SC999.getMsg());
        }
        //获取公钥
        String publicKey = paCfgSystemMapper.selectByPrimaryKey(StatusCodeEnum.RSA_PUBLICKEY.getCode()).getFldval();
        //加密
        String encrypt = RSAUtil.encrypt((request.getUserCode()+request.getNewPwd()).getBytes(), publicKey);
        paUserDo.setPassword(encrypt);
        paUserDo.setIschange(1);
        paUserMapper.updateByPrimaryKeySelective(paUserDo);
    }


}

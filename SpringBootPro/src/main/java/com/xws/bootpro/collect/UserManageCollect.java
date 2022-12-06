package com.xws.bootpro.collect;

import com.xws.bootpro.dataobj.*;
import com.xws.bootpro.dataobj.DTO.UserListDto;
import com.xws.bootpro.dataobj.DTO.WaresTypeDto;
import com.xws.bootpro.dataobj.Enum.StatusCodeEnum;
import com.xws.bootpro.dataobj.request.EditUserRequestDto;
import com.xws.bootpro.dataobj.request.SelectRequestDto;
import com.xws.bootpro.mapper.*;
import com.xws.bootpro.utils.CreateHttp.SysContent;
import com.xws.bootpro.utils.CustomException;
import com.xws.bootpro.utils.DateUtil;
import com.xws.bootpro.utils.RSA.RSAUtil;
import com.xws.bootpro.utils.export.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.*;

/**
 *@Author：lm
 *@Date：2022/8/16 14:46
 *@Description 用户管理类
*/

@Slf4j
@RestController
@RequestMapping("api/userManage")
public class UserManageCollect {

    //本系统的访问路径
    @Value("${csrf.baseurl}")
    private String baseurL;

    @Resource
    PaUserMapper paUserMapper;

    @Resource
    PaUserRoleMapper paUserRoleMapper;

    @Resource
    PaUserSessionMapper paUserSessionMapper;

    @Resource
    PaRoleMapper paRoleMapper;

    @Resource
    PaCfgSystemMapper paCfgSystemMapper;

    /**
     * 查询列表
     */
    @RequestMapping("selectUserList")
    public List<UserListDto> selectUserList(@RequestBody SelectRequestDto request) throws Exception{
        String keyword = request.getKeyword();
        if(StringUtils.isEmpty(keyword)){
            keyword = "%%";
        }else{
            keyword = "%"+keyword+"%";
        }
        return paUserMapper.selectUserList(keyword);
    }

    /**
     * 导出用户信息
     */
    @RequestMapping("ExportUserList")
    public void ExportUserList(@RequestBody SelectRequestDto request) throws Exception{
        String keyword = request.getKeyword();
        if(StringUtils.isEmpty(keyword)){
            keyword = "%%";
        }else{
            keyword = "%"+keyword+"%";
        }
        List<UserListDto> list = paUserMapper.selectUserList(keyword);

        List<String> columnList = Arrays.asList("序号","用户账号","用户名称","角色","状态","创建时间");

        List<List<String>> dataList = new ArrayList<>();
        if(list.size() > 0){
            for (UserListDto dto : list){
                List<String> str = new ArrayList<>();
                str.add(dto.getUsercode());
                str.add(dto.getUsername());
                str.add(dto.getRolename());
                String state = "无效";
                if(dto.getState() == 0){
                    state = "有效";
                }else if(dto.getState() == 2){
                    state = "锁定";
                }
                str.add(state);
                str.add(dto.getCreatetime());
                dataList.add(str);
            }
        }

        ExcelUtil.uploadExcelAboutUser(SysContent.getResponse(),columnList,dataList,baseurL);
        log.info("用户：" + request.getUserCode() + " 在" + DateUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss") +" 导出用户信息表");
    }

    /**
     * 编辑用户
     */
    @RequestMapping("editUser")
    public void editUser(@RequestBody EditUserRequestDto request) throws Exception{
        PaUserDo user = new PaUserDo();
        user.setUsercode(request.getUserId());
        user.setUsername(request.getUserName());
        user.setState(request.getState());

        if(request.getType().equals("add")){
            PaUserDo userDo = new PaUserDo();
            userDo.setUsercode(request.getUserId());
            List<PaUserDo> users = paUserMapper.select(userDo);
            if(users.size() > 0){
                throw new CustomException(StatusCodeEnum.SC103.getCode(),StatusCodeEnum.SC103.getMsg());
            }

            //新增
            user.setUserlevel(0);
            //获取公钥
            String publicKey = paCfgSystemMapper.selectByPrimaryKey(StatusCodeEnum.RSA_PUBLICKEY.getCode()).getFldval();
            //加密
            String encrypt = RSAUtil.encrypt((request.getUserId() + paCfgSystemMapper.selectByPrimaryKey(StatusCodeEnum.DEFAULT_PASSWORD.getCode()).getFldval()).getBytes(), publicKey);
            user.setPassword(encrypt);
            user.setCreatetime(DateUtil.dateToTime(new Date(),"yyyy-MM-dd HH:mm:ss"));
            paUserMapper.insertSelective(user);

            PaUserSessionDo paUserSessionDo = new PaUserSessionDo();
            paUserSessionDo.setUsercode(request.getUserId());
            paUserSessionMapper.insert(paUserSessionDo);
        }else {
            //修改
            paUserMapper.updateByPrimaryKeySelective(user);
        }

        //用户-角色管理表
        PaUserRoleDo userRoleDo = new PaUserRoleDo();
        userRoleDo.setUsercode(request.getUserId());
        userRoleDo = paUserRoleMapper.select(userRoleDo).size() > 0 ? paUserRoleMapper.select(userRoleDo).get(0) : null;

        PaUserRoleDo userRole = new PaUserRoleDo();
        userRole.setUsercode(request.getUserId());
        userRole.setRoleid(request.getRoleId());

        if(null == userRoleDo){
            //新增
            paUserRoleMapper.insert(userRole);
        }else{
            //修改
            paUserRoleMapper.updateByPrimaryKey(userRole);
        }
    }

    /**
     *删除用户
     */
    @RequestMapping("deleteUser")
    public void deleteUser(@RequestBody EditUserRequestDto request) throws Exception{
        PaUserDo user = new PaUserDo();
        user.setUsercode(request.getUserId());
        user.setState(1);
        paUserMapper.updateByPrimaryKeySelective(user);
    }

    /**
     *重置密码
     */
    @RequestMapping("resetPassWord")
    public void resetPassWord(@RequestBody EditUserRequestDto request) throws Exception{
        PaUserDo user = new PaUserDo();
        user.setUsercode(request.getUserId());
        //获取公钥
        String publicKey = paCfgSystemMapper.selectByPrimaryKey(StatusCodeEnum.RSA_PUBLICKEY.getCode()).getFldval();
        //加密
        String encrypt = RSAUtil.encrypt((request.getUserId() + paCfgSystemMapper.selectByPrimaryKey(StatusCodeEnum.DEFAULT_PASSWORD.getCode()).getFldval()).getBytes(), publicKey);
        user.setPassword(encrypt);
        user.setIschange(0);
        paUserMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * 用户详情
     */
    @RequestMapping("userinfo")
    public UserListDto userinfo(@RequestBody SelectRequestDto request) throws Exception{
        UserListDto info = new UserListDto();

        if(!request.getUserId().equals("-1")){
            info = paUserMapper.selectUserInfo(request.getUserId());
        }

        //状态集合
        List<PaStateDo> stateList = new ArrayList<>();
        PaStateDo st1 = new PaStateDo();
        st1.setStateid(0);
        st1.setStatename("有效");
        stateList.add(st1);
        PaStateDo st2 = new PaStateDo();
        st2.setStateid(1);
        st2.setStatename("无效");
        stateList.add(st2);
        PaStateDo st3 = new PaStateDo();
        st3.setStateid(2);
        st3.setStatename("锁定");
        stateList.add(st3);
        info.setStateList(stateList);

        //获取角色列表
        List<PaRoleDo> roleDos = paRoleMapper.selectAll();
        info.setRoleList(roleDos);

        return info;
    }

}

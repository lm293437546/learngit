package com.xws.bootpro.mapper;

import com.xws.bootpro.dataobj.DTO.UserListDto;
import com.xws.bootpro.dataobj.PaUserDo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PaUserMapper extends Mapper<PaUserDo> {

    @Select("select a.usercode,a.username,a.state,a.userlevel,a.createtime,b.roleid,b.rolename from " +
            "pa_user a left join pa_user_role c on a.usercode = c.usercode " +
            "left join pa_role b on c.roleid = b.roleid " +
            "where a.username like #{keyword} or a.usercode like #{keyword} order by a.createtime desc")
    List<UserListDto> selectUserList(@Param("keyword") String keyword);

    @Select("select a.usercode,a.username,a.state,a.userlevel,a.createtime,b.roleid,b.rolename from " +
            "pa_user a left join pa_user_role c on a.usercode = c.usercode " +
            "left join pa_role b on c.roleid = b.roleid " +
            "where a.userCode like #{userCode}")
    UserListDto selectUserInfo(@Param("userCode") String userCode);

}

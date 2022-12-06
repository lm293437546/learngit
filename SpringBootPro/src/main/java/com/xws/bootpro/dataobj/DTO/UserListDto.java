package com.xws.bootpro.dataobj.DTO;

import com.xws.bootpro.dataobj.PaRoleDo;
import com.xws.bootpro.dataobj.PaStateDo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedList;
import java.util.List;

/**
 *@Author：lm
 *@Date：2022/8/16 15:01
 *@Description 用户类
*/

@Setter
@Getter
@ToString
public class UserListDto {

    private String usercode;

    private String username;

    private String password;

    private Integer state;

    private Integer userlevel;

    private String createtime;

    private Integer roleid;

    private String rolename;

    private List<PaRoleDo> roleList = new LinkedList<>();

    private List<PaStateDo> stateList = new LinkedList<>();

}

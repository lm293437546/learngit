package com.xws.bootpro.dataobj.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *@Author：lm
 *@Date：2022/8/16 15:00
 *@Description 编辑用户类
*/

@Setter
@Getter
@ToString
public class EditUserRequestDto {

    /**
     * 用户code
     */
    private String userCode;

    private String userId;

    private String userName;

    private String passWord;

    private Integer state;

    private Integer userlevel;

    private Integer roleId;

    private String type;  //add:新增，update：修改

}

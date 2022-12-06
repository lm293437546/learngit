package com.xws.bootpro.dataobj;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *@Author：lm
 *@Date：2022/8/10 14:52
 *@Description 用户登录session信息表
*/

@Getter
@Setter
@ToString
@Table(name = "pa_user_session")
@Data
public class PaUserSessionDo {

    @Id
    @Column(name = "usercode")
    private String usercode;

    private String sessionid;

}

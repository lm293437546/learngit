package com.xws.bootpro.dataobj;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 *@Author：lm
 *@Date：2022/8/10 14:52
 *@Description 用户信息表
*/

@Getter
@Setter
@ToString
@Table(name = "pa_user")
@Data
public class PaUserDo {

    @Id
    @Column(name = "usercode")
    private String usercode;

    private String username;

    private String password;

    private Integer state;

    private Integer userlevel;

    private Timestamp createtime;

    private Integer loginerrorcount;

    private Timestamp lastloginerrortime;

    private Integer ischange;

}

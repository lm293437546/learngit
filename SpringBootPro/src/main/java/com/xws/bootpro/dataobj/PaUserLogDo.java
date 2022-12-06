package com.xws.bootpro.dataobj;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 *@Author：lm
 *@Date：2022/8/10 14:52
 *@Description 用户登录记录表
*/

@Getter
@Setter
@ToString
@Table(name = "pa_user_log")
@Data
public class PaUserLogDo {

    private String usercode;

    private String username;

    private String ip;

    private String addr;

    private Timestamp logtime;

    private String reserve1;

    private String reserve2;

    private String reserve3;

}

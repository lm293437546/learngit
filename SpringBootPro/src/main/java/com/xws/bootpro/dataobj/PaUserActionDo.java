package com.xws.bootpro.dataobj;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Table;
import java.sql.Timestamp;

/**
 *@Author：lm
 *@Date：2022/8/10 14:52
 *@Description 用户操作记录表
*/

@Getter
@Setter
@ToString
@Table(name = "pa_user_action")
@Data
public class PaUserActionDo {

    private String usercode;

    private String requesturl;

    private Timestamp actiontime;

    private String reserve1;

    private String reserve2;

    private String reserve3;

}

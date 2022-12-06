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
 *@Description 角色信息表
*/

@Getter
@Setter
@ToString
@Table(name = "pa_role")
@Data
public class PaRoleDo {

    @Id
    @Column(name = "roleid")
    private Integer roleid;

    private String rolename;

    private Timestamp createtime;

    private String remark;

    private String reserve;

}

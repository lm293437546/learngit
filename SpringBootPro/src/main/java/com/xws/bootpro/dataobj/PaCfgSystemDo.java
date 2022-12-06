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
 *@Date：2022/11/25 15:40
 *@Description 系统常量配置项表
*/

@Getter
@Setter
@ToString
@Table(name = "pa_cfg_system")
@Data
public class PaCfgSystemDo {

    @Id
    @Column(name = "fldname")
    private String fldname;

    private String fldval;

    private String flddesc;

}

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
 *@Date：2022/11/29 17:01
 *@Description 定时任务配置表
*/

@Getter
@Setter
@ToString
@Table(name = "pa_scheduled")
@Data
public class PaScheduledDo {

    @Id
    @Column(name = "id")
    private Integer id;

    private String taskKey;

    private String name;

    private String cron;

    private String remark;

    private Integer status;

    private Timestamp createTime;

    private Timestamp updateTime;

}

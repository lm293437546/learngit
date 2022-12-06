package com.xws.bootpro.dataobj;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 *@Author：lm
 *@Date：2022/8/10 14:52
 *@Description 成交记录表
*/

@Getter
@Setter
@ToString
@Table(name = "pa_buys")
@Data
public class PaBuysDo {

    @Id
    @Column(name = "buyno")
    private String buyno;

    private String buyer;

    private String warescode;

    private Date buytime;

    private Integer gmv;

    private String reserve1;

    private String reserve2;

    private String reserve3;

}

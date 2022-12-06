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
 *@Description 商品浏览记录表
*/

@Getter
@Setter
@ToString
@Table(name = "pa_wares_action")
@Data
public class PaWaresActionDo {

    private String userip;

    private String useraddr;

    private String warescode;

    private Timestamp actiontime;

    private String reserve1;

    private String reserve2;

    private String reserve3;

}

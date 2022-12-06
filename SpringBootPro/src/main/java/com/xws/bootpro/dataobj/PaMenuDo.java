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
 *@Description 菜单信息表
*/

@Getter
@Setter
@ToString
@Table(name = "pa_menu")
@Data
public class PaMenuDo {

    @Id
    @Column(name = "menuid")
    private Integer menuid;

    private Integer parentid;

    private String menuname;

    private String addr;

    private Integer sort;

    private String icon;

    private Timestamp createtime;

    private String remark;

    private String reserve;

}

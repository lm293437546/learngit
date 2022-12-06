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
 *@Description 菜单和角色关联表
*/

@Getter
@Setter
@ToString
@Table(name = "pa_menu_role")
@Data
public class PaMenuRoleDo {

    @Id
    @Column(name = "roleid")
    private Integer roleid;

    private Integer menuid;

}

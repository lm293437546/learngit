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
 *@Description 商品类型表
*/

@Getter
@Setter
@ToString
@Table(name = "pa_wares_type")
@Data
public class PaWaresTypeDo {

    @Id
    @Column(name = "warescode")
    private String warescode;

    private String waresname;

    private String reserve1;

    private Integer reserve2;

    private String reserve3;

}

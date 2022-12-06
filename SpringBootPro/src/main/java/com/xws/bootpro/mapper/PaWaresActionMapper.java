package com.xws.bootpro.mapper;

import com.xws.bootpro.dataobj.DTO.WaresActionDto;
import com.xws.bootpro.dataobj.PaWaresActionDo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PaWaresActionMapper extends Mapper<PaWaresActionDo> {

    @Select("select a.userip,a.useraddr,a.warescode,b.waresname,a.actiontime,a.reserve1,a.reserve2,a.reserve3 " +
            "from pa_wares_action a left join pa_wares_type b on a.warescode = b.warescode " +
            "where a.warescode like #{keyword} order by a.actiontime desc")
    List<WaresActionDto> selectWaresActionList(@Param("keyword") String keyword);

}

package com.xws.bootpro.mapper;

import com.xws.bootpro.dataobj.DTO.WaresTypeDto;
import com.xws.bootpro.dataobj.PaWaresTypeDo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PaWaresTypeMapper extends Mapper<PaWaresTypeDo> {

    @Select("select a.warescode,a.waresname,a.reserve1,a.reserve2,a.reserve3 from pa_wares_type a " +
            "where a.warescode like #{keyword} or a.waresname like #{keyword} order by a.reserve2 desc")
    List<WaresTypeDto> selectWaresTypeList(@Param("keyword") String keyword);

    @Select("select max(reserve2) from pa_wares_type")
    String getMaxNo();

}

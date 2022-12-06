package com.xws.bootpro.mapper;

import com.xws.bootpro.dataobj.DTO.LogListDto;
import com.xws.bootpro.dataobj.PaUserLogDo;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PaUserLogMapper extends Mapper<PaUserLogDo> {

    @Select("select usercode,username,ip,addr,logtime from pa_user_log order by logtime desc")
    List<LogListDto> selectallLog();

}

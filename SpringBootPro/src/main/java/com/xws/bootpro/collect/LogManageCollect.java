package com.xws.bootpro.collect;

import com.xws.bootpro.dataobj.DTO.LogListDto;
import com.xws.bootpro.dataobj.DTO.WaresTypeDto;
import com.xws.bootpro.dataobj.request.SelectRequestDto;
import com.xws.bootpro.mapper.PaUserLogMapper;
import com.xws.bootpro.utils.CreateHttp.SysContent;
import com.xws.bootpro.utils.DateUtil;
import com.xws.bootpro.utils.export.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 *@Author：lm
 *@Date：2022/8/18 14:42
 *@Description 用户登录记录类
*/

@Slf4j
@RestController
@RequestMapping("api/logManage")
public class LogManageCollect {

    //本系统的访问路径
    @Value("${csrf.baseurl}")
    private String baseurL;

    @Resource
    PaUserLogMapper paUserLogMapper;

    /**
     * 查询列表
     */
    @RequestMapping("selectLogList")
    public List<LogListDto> selectMenuList(@RequestBody SelectRequestDto request) throws Exception{
        return paUserLogMapper.selectallLog();
    }

    /**
     * 导出登录信息表
     */
    @RequestMapping("ExportLogList")
    public void ExportWaresTypeList(@RequestBody SelectRequestDto request) throws Exception{
        List<LogListDto> list = paUserLogMapper.selectallLog();

        List<String> columnList = Arrays.asList("序号","登录用户名","用户姓名","IP","地址","登录时间");

        List<List<String>> dataList = new ArrayList<>();
        if(list.size() > 0){
            for (LogListDto dto : list){
                List<String> str = new ArrayList<>();
                str.add(dto.getUsercode());
                str.add(dto.getUsername());
                str.add(dto.getIp());
                str.add(dto.getAddr());
                str.add(dto.getLogtime());
                dataList.add(str);
            }
        }

        ExcelUtil.uploadExcelAboutUser(SysContent.getResponse(),columnList,dataList,baseurL);
        log.info("用户：" + request.getUserCode() + " 在" + DateUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss") +" 导出登录信息表");
    }

}

package com.xws.bootpro.collect;

import com.xws.bootpro.dataobj.DTO.*;
import com.xws.bootpro.dataobj.Enum.StatusCodeEnum;
import com.xws.bootpro.dataobj.PaBuysDo;
import com.xws.bootpro.dataobj.PaWaresTypeDo;
import com.xws.bootpro.dataobj.request.EditBuysRequestDto;
import com.xws.bootpro.dataobj.request.EditWaresTypeRequestDto;
import com.xws.bootpro.dataobj.request.SelectRequestDto;
import com.xws.bootpro.mapper.*;
import com.xws.bootpro.utils.CreateHttp.SysContent;
import com.xws.bootpro.utils.CustomException;
import com.xws.bootpro.utils.DateUtil;
import com.xws.bootpro.utils.export.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

/**
 *@Author：lm
 *@Date：2022/9/1 15:26
 *@Description 商品操作类
*/

@Slf4j
@RestController
@RequestMapping("api/waresManage")
public class WaresCollect {

    //本系统的访问路径
    @Value("${csrf.baseurl}")
    private String baseurL;

    @Resource
    PaWaresTypeMapper paWaresTypeMapper;

    @Resource
    PaWaresActionMapper paWaresActionMapper;

    @Resource
    PaBuysMapper paBuysMapper;

    @Resource
    PaCfgSystemMapper paCfgSystemMapper;

    /**
     * 查询商品类型列表
     */
    @RequestMapping("selectWaresTypeList")
    public List<WaresTypeDto> selectWaresTypeList(@RequestBody SelectRequestDto request) throws Exception{
        String keyword = request.getKeyword();
        if(StringUtils.isEmpty(keyword)){
            keyword = "%%";
        }else{
            keyword = "%"+keyword+"%";
        }
        return paWaresTypeMapper.selectWaresTypeList(keyword);
    }

    /**
     *商品类型详情
     */
    @RequestMapping("waresTypeinfo")
    public PaWaresTypeDo waresTypeinfo(@RequestBody SelectRequestDto request) throws Exception{
        return paWaresTypeMapper.selectByPrimaryKey(request.getId()) == null ? new PaWaresTypeDo() : paWaresTypeMapper.selectByPrimaryKey(request.getId());
    }

    /**
     * 编辑商品类型
     */
    @RequestMapping("editWaresType")
    public void editWaresType(@RequestBody EditWaresTypeRequestDto request) throws Exception{
        PaWaresTypeDo paWaresTypeDo = new PaWaresTypeDo();
        paWaresTypeDo.setWarescode(request.getWarescode());
        paWaresTypeDo.setWaresname(request.getWaresname());
        paWaresTypeDo.setReserve1(request.getReserve1());
        if(request.getType().equals("add")){
            PaWaresTypeDo pa = paWaresTypeMapper.selectByPrimaryKey(request.getWarescode());
            if(null != pa){
                throw new CustomException(StatusCodeEnum.SC105.getCode(),StatusCodeEnum.SC105.getMsg());
            }
            //自动生成排序号
            String max = paWaresTypeMapper.getMaxNo();
            if (max == null){
                paWaresTypeDo.setReserve2(1);
            }else{
                paWaresTypeDo.setReserve2(Integer.parseInt(max) + 1);
            }

            //新增
            paWaresTypeMapper.insert(paWaresTypeDo);
        }else{
            //修改
            paWaresTypeMapper.updateByPrimaryKeySelective(paWaresTypeDo);
        }
    }

    /**
     * 删除商品类型
     */
    @RequestMapping("deleteWaresType")
    public void deleteWaresType(@RequestBody EditWaresTypeRequestDto request) throws Exception{
        paWaresTypeMapper.deleteByPrimaryKey(request.getWarescode());
    }

    /**
     * 导出商品类型列表
     */
    @RequestMapping("ExportWaresTypeList")
    public void ExportWaresTypeList(@RequestBody SelectRequestDto request) throws Exception{
        String keyword = request.getKeyword();
        if(StringUtils.isEmpty(keyword)){
            keyword = "%%";
        }else{
            keyword = "%"+keyword+"%";
        }
        List<WaresTypeDto> list = paWaresTypeMapper.selectWaresTypeList(keyword);

        List<String> columnList = Arrays.asList("序号","商品编码","商品名称","备注");

        List<List<String>> dataList = new ArrayList<>();
        if(list.size() > 0){
            for (WaresTypeDto dto : list){
                List<String> str = new ArrayList<>();
                str.add(dto.getWarescode());
                str.add(dto.getWaresname());
                str.add(dto.getReserve1());
                dataList.add(str);
            }
        }

        ExcelUtil.uploadExcelAboutUser(SysContent.getResponse(),columnList,dataList,baseurL);
        log.info("用户：" + request.getUserCode() + " 在" + DateUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss") +" 导出商品类型表");
    }

    /**
     * 查询商品浏览记录列表
     */
    @RequestMapping("selectWaresActionList")
    public List<WaresActionDto> selectWaresActionList(@RequestBody SelectRequestDto request) throws Exception{
        String keyword = request.getKeyword();
        if(StringUtils.isEmpty(keyword)){
            keyword = "%%";
        }else{
            keyword = "%"+keyword+"%";
        }
        return paWaresActionMapper.selectWaresActionList(keyword);
    }

    /**
     * 导出商品浏览记录
     */
    @RequestMapping("ExportWaresActionList")
    public void ExportWaresActionList(@RequestBody SelectRequestDto request) throws Exception{
        String keyword = request.getKeyword();
        if(StringUtils.isEmpty(keyword)){
            keyword = "%%";
        }else{
            keyword = "%"+keyword+"%";
        }
        List<WaresActionDto> list = paWaresActionMapper.selectWaresActionList(keyword);

        List<String> columnList = Arrays.asList("序号","用户ip","用户地址","商品编码","商品名称","浏览时间");

        List<List<String>> dataList = new ArrayList<>();
        if(list.size() > 0){
            for (WaresActionDto dto : list){
                List<String> str = new ArrayList<>();
                str.add(dto.getUserip());
                str.add(dto.getUseraddr());
                str.add(dto.getWarescode());
                str.add(dto.getWaresname());
                str.add(dto.getActiontime());
                dataList.add(str);
            }
        }

        ExcelUtil.uploadExcelAboutUser(SysContent.getResponse(),columnList,dataList,baseurL);
        log.info("用户：" + request.getUserCode() + " 在" + DateUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss") +" 导出商品浏览记录表");
    }

    /**
     * 查询成交记录列表
     */
    @RequestMapping("selectBuysList")
    public List<BuysDto> selectBuysList(@RequestBody SelectRequestDto request) throws Exception{
        String keyword = request.getKeyword();
        if(StringUtils.isEmpty(keyword)){
            keyword = "%%";
        }else{
            keyword = "%"+keyword+"%";
        }
        return paBuysMapper.selectBuysList(keyword);
    }

    /**
     * 编辑商品成交
     */
    @RequestMapping("editBuys")
    public void editBuys(@RequestBody EditBuysRequestDto request) throws Exception{
        PaBuysDo paBuysDo = new PaBuysDo();
        paBuysDo.setBuyer(request.getBuyer());
        paBuysDo.setWarescode(request.getWarescode());
        paBuysDo.setBuytime(DateUtil.strToUtilDate(request.getBuytime(),"yyyy-MM-dd"));
        paBuysDo.setGmv(Integer.parseInt(request.getGmv()));
        paBuysDo.setReserve1(request.getReserve1());
        if(request.getType().equals("add")){
            paBuysDo.setBuyno(DateUtil.dateToStr(new Date(),"yyyyMMddHHmmss"));
            //新增
            paBuysMapper.insertSelective(paBuysDo);
        }else{
            //修改
            paBuysDo.setBuyno(request.getBuyno());
            paBuysMapper.updateByPrimaryKeySelective(paBuysDo);
        }
    }

    /**
     * 删除商品成交
     */
    @RequestMapping("deleteBuys")
    public void deleteBuys(@RequestBody EditBuysRequestDto request) throws Exception{
        paBuysMapper.deleteByPrimaryKey(request.getBuyno());
    }

    /**
     * 导出商品成交
     */
    @RequestMapping("ExportBuysList")
    public void ExportBuysList(@RequestBody SelectRequestDto request) throws Exception{
        String keyword = request.getKeyword();
        if(StringUtils.isEmpty(keyword)){
            keyword = "%%";
        }else{
            keyword = "%"+keyword+"%";
        }
        List<BuysDto> list = paBuysMapper.selectBuysList(keyword);

        List<String> columnList = Arrays.asList("序号","购买人","商品编码","商品名称","成交金额","购买时间","备注");

        List<List<String>> dataList = new ArrayList<>();
        if(list.size() > 0){
            for (BuysDto dto : list){
                List<String> str = new ArrayList<>();
                str.add(dto.getBuyer());
                str.add(dto.getWarescode());
                str.add(dto.getWaresname());
                str.add(dto.getGmv().toString());
                str.add(dto.getBuytime());
                str.add(dto.getReserve1());
                dataList.add(str);
            }
        }

        ExcelUtil.uploadExcelAboutUser(SysContent.getResponse(),columnList,dataList,baseurL);
        log.info("用户：" + request.getUserCode() + " 在" + DateUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss") +" 导出商品成交表");
    }

    /**
     *商品成交详情
     */
    @RequestMapping("buysinfo")
    public BuysDto buysinfo(@RequestBody SelectRequestDto request) throws Exception{
        BuysDto dto = new BuysDto();
        if(!request.getId().equals("-1")){
            PaBuysDo paBuysDo = paBuysMapper.selectByPrimaryKey(request.getId());
            dto.setBuyno(paBuysDo.getBuyno());
            dto.setBuyer(paBuysDo.getBuyer());
            dto.setGmv(paBuysDo.getGmv());
            dto.setWarescode(paBuysDo.getWarescode());
            dto.setReserve1(paBuysDo.getReserve1());
            dto.setBuytime(DateUtil.dateToStr(paBuysDo.getBuytime(),"yyyy-MM-dd"));
        }
        //获取商品类型集合
        List<WaresTypeDto> types = paWaresTypeMapper.selectWaresTypeList("%%");
        dto.setTypes(types);
        return dto;
    }

    /**
     * 首页统计
     */
    @RequestMapping("homePageCount")
    public HomeCountDto homePageCount(@RequestBody SelectRequestDto request) throws Exception{
        HomeCountDto homeCountDto = new HomeCountDto();

        //总数总数
        HomeCount1Dto homeCount1Dto = new HomeCount1Dto();
        homeCount1Dto.setUserCount(paBuysMapper.getUserCount());
        homeCount1Dto.setWaresCount(paBuysMapper.getWaresCount());
        homeCount1Dto.setBrowseCount(paBuysMapper.getBrowseCount());
        homeCount1Dto.setRecommendCount(paBuysMapper.getRecommendCount());

        //各商品浏览次数
        List<HomeCount2Dto> homeCount2Dtos = paBuysMapper.getHomeCount2Dtos();

        //收益数据
        HomeCount3Dto homeCount3Dto = new HomeCount3Dto();
        //当月
        String month = DateUtil.dateToStr(new Date(),"yyyy-MM") + "-00";
        //当日
        String day  = DateUtil.dateToStr(new Date(),"yyyy-MM-dd");
        homeCount3Dto.setRevenueTotal(paBuysMapper.getRevenueTotal());
        homeCount3Dto.setRevenueMonth(paBuysMapper.getRevenueMonth(month));
        homeCount3Dto.setRevenueDay(paBuysMapper.getRevenueDay(day));
        homeCount3Dto.setDealTotal(paBuysMapper.getDealTotal());
        homeCount3Dto.setDealMonth(paBuysMapper.getDealMonth(month));
        homeCount3Dto.setDealDay(paBuysMapper.getDealDay(day));

        //商品信息
        List<HomeCount4Dto> homeCount4Dtos = paBuysMapper.getHomeCount4Dtos();

        homeCountDto.setHomeCount1Dto(homeCount1Dto);
        homeCountDto.setHomeCount2Dtos(homeCount2Dtos);
        homeCountDto.setHomeCount3Dto(homeCount3Dto);
        homeCountDto.setHomeCount4Dtos(homeCount4Dtos);
        return homeCountDto;
    }

}

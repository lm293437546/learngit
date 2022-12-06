package com.xws.bootpro.mapper;

import com.xws.bootpro.dataobj.DTO.BuysDto;
import com.xws.bootpro.dataobj.DTO.HomeCount2Dto;
import com.xws.bootpro.dataobj.DTO.HomeCount4Dto;
import com.xws.bootpro.dataobj.PaBuysDo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PaBuysMapper extends Mapper<PaBuysDo> {

    @Select("select a.buyno,a.buyer,a.warescode,b.waresname,a.buytime,a.gmv,a.reserve1,a.reserve2,a.reserve3 " +
            "from pa_buys a left join pa_wares_type b on a.warescode = b.warescode " +
            "where a.warescode like #{keyword} order by a.buytime desc")
    List<BuysDto> selectBuysList(@Param("keyword") String keyword);

    //用户数量
    @Select("select count(*) from pa_user")
    Integer getUserCount();

    //商品数量
    @Select("select count(*) from pa_wares_type")
    Integer getWaresCount();

    //用户浏览商品次数
    @Select("select count(*) from pa_wares_action")
    Integer getBrowseCount();

    //推荐成交次数
    @Select("select count(*) from pa_buys where reserve1 = '推荐成交'")
    Integer getRecommendCount();

    //首页统计（各商品浏览次数）
    @Select("SELECT\n" +
            "\tb.warescode AS waresType,\n" +
            "IF\n" +
            "\t( c.waresCount IS NULL, 0, c.waresCount ) AS waresCount \n" +
            "FROM\n" +
            "\tpa_wares_type b\n" +
            "\tLEFT JOIN ( SELECT a.warescode, count( a.warescode ) AS waresCount FROM pa_wares_action a GROUP BY a.warescode ) c ON c.warescode = b.warescode \n" +
            "ORDER BY\n" +
            "\tb.reserve2")
    List<HomeCount2Dto> getHomeCount2Dtos();

    //总收益
    @Select("select if(SUM(gmv) is null ,0, SUM(gmv)) from pa_buys")
    Integer getRevenueTotal();

    //当月收益
    @Select("select if(SUM(gmv) is null ,0, SUM(gmv)) from pa_buys where STR_TO_DATE(buytime,'%Y-%m') = #{month}")
    Integer getRevenueMonth(@Param("month") String month);

    //当日收益
    @Select("select if(SUM(gmv) is null ,0, SUM(gmv)) from pa_buys where STR_TO_DATE(buytime,'%Y-%m-%d') = #{day}")
    Integer getRevenueDay(@Param("day") String day);

    //总成交量
    @Select("select count(*) from pa_buys")
    Integer getDealTotal();

    //当月成交量
    @Select("select count(*) from pa_buys where STR_TO_DATE(buytime,'%Y-%m') = #{month}")
    Integer getDealMonth(@Param("month") String month);

    //当日收益
    @Select("select count(*) from pa_buys where STR_TO_DATE(buytime,'%Y-%m-%d') = #{day}")
    Integer getDealDay(@Param("day") String day);

    //首页统计（商品信息）
    @Select("SELECT\n" +
            "\tb.warescode AS bh,\n" +
            "IF\n" +
            "\t( e.deal IS NULL, 0, e.deal ) AS deal,\n" +
            "IF\n" +
            "\t( d.recommend IS NULL, 0, d.recommend ) AS recommend,\n" +
            "\t200 AS price,\n" +
            "IF\n" +
            "\t( c.income IS NULL, 0, c.income ) AS income \n" +
            "FROM\n" +
            "\tpa_wares_type b\n" +
            "\tLEFT JOIN ( SELECT a.warescode, SUM( a.gmv ) AS income FROM pa_buys a GROUP BY a.warescode ) c ON c.warescode = b.warescode\n" +
            "\tLEFT JOIN (\n" +
            "SELECT\n" +
            "\ta.warescode,\n" +
            "\tcount( * ) AS recommend \n" +
            "FROM\n" +
            "\tpa_buys a \n" +
            "WHERE\n" +
            "\ta.reserve1 = '推荐成交' \n" +
            "GROUP BY\n" +
            "\ta.warescode \n" +
            "\t) d ON d.warescode = b.warescode\n" +
            "\tLEFT JOIN ( SELECT a.warescode, count( * ) AS deal FROM pa_buys a GROUP BY a.warescode ) e ON e.warescode = b.warescode \n" +
            "ORDER BY\n" +
            "\tb.reserve2")
    List<HomeCount4Dto> getHomeCount4Dtos();

}

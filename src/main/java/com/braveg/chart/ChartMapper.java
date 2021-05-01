package com.braveg.chart;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ChartMapper {
    List<ChartDto> getTodayRankList() throws Exception;
    List<ChartDto> getDateRankList() throws Exception;
    List<ChartDto> getRankForGraphList(String site) throws Exception;

    int insertEachHour(ChartDto dto) throws Exception;
    int insertDate(ChartDto dto) throws Exception;

    int resetEachHour() throws Exception;

    List<ChartDto>dailyRankVariation(ChartDto dto) throws Exception;
    List<ChartDto>monthlyRankVariation(ChartDto dto) throws Exception;

    /** ///////////////////    Visitor    /////////////////// **/
    int insertVisitor(VisitDto dto);
    int getTodayCnt();
    int getTotalCnt();

}

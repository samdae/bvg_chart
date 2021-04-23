package com.braveg.chart;

import org.json.simple.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

public interface ChartService {
    List<ChartDto> getTodayRankList() throws Exception;
    List<ChartDto> getRankForGraphList(String site) throws Exception;
    List<ChartDto> getDateRankList() throws Exception;

    int insertEachHour(ChartDto dto) throws Exception;
    int insertDate(ChartDto dto) throws Exception;

    int resetEachHour() throws Exception;

    List<ChartDto>dailyRankVariation() throws Exception;
    JSONObject monthlyRankVariation(ChartDto dto) throws Exception;

    /** ///////////////////    Visitor    /////////////////// **/
    int insertVisitor(VisitDto dto);
    int getTodayCnt();
    int getTotalCnt();
}

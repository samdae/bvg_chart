package com.braveg.chart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChartServiceImpl implements ChartService {

    @Autowired
    private ChartMapper chartMapper;

    @Override
    public List<ChartDto> getTodayRankList() throws Exception {
        List<ChartDto> list = null;
        try{
            list = chartMapper.getTodayRankList();
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<ChartDto> getDateRankList() throws Exception {
        List<ChartDto> list = null;
        try{
            list = chartMapper.getDateRankList();
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int insertEachHour(ChartDto dto) throws Exception {
        return chartMapper.insertEachHour(dto);
    }

    @Override
    public int insertDate(ChartDto dto) throws Exception {
        return chartMapper.insertDate(dto);
    }

    @Override
    public int resetEachHour() throws Exception {
        return chartMapper.resetEachHour();
    }


    /** ///////////////////    Visitor    /////////////////// **/

    @Override
    public int insertVisitor(VisitDto dto) {
        return chartMapper.insertVisitor(dto);
    }

    @Override
    public int getTodayCnt() {
        return chartMapper.getTodayCnt();
    }

    @Override
    public int getTotalCnt() {
        return chartMapper.getTotalCnt();
    }


}

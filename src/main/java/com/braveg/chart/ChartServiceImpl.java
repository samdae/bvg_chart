package com.braveg.chart;

import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
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
    public List<ChartDto> getRankForGraphList(String site) throws Exception {
        List<ChartDto> list = null;
        try{
            list = chartMapper.getRankForGraphList(site);
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

    @Override
    public List<ChartDto> dailyRankVariation() throws Exception {
        return chartMapper.dailyRankVariation();
    }

    @Override
    public JSONObject monthlyRankVariation(ChartDto dto) throws Exception {
        List<ChartDto> list = null;
        JSONObject data = new JSONObject();
try {
    list = chartMapper.monthlyRankVariation(dto);
    List<String> nameArr = new ArrayList<>();
    List<String> dateArr = new ArrayList<>();
    for( ChartDto d : list ){
        nameArr.add( d.getTitle() );
        dateArr.add( d.getSdate() );
    }

    // TODO
    org.json.simple.JSONArray dateLables = new org.json.simple.JSONArray();

    // 중복제거 hashset create
    HashSet<String> hashSet1 = new HashSet<>(nameArr);
    HashSet<String> hashSet2 = new HashSet<>(dateArr);
    // 곡명 중복 제거
    Object[] titles = hashSet1.toArray();
    // date 중복제거
    Object[] dates = hashSet2.toArray();

    // set dateLabels
    for (Object q : dates) dateLables.add( q.toString() );

    // TODO arr.size == titles.length
    org.json.simple.JSONArray datasets = new org.json.simple.JSONArray(); // put json object

    for (int i = 0; i < titles.length; i++) {
        // 각 곡별 dataSet
        JSONObject obj = new JSONObject();
        org.json.simple.JSONArray rank = new org.json.simple.JSONArray();
        String x = titles[i].toString();
        System.out.println(x);

        for (ChartDto d : list) {
            if (x.equals(d.getTitle())) {
                // obj 에 해당 곡이 없을 때
                if (!obj.containsKey("label")) {
                    obj.put("label", d.getTitle());
                    obj.put("borderColor", getRGB(i) ); // TODO 하드코딩 테스트
                }
                rank.add(d.getRanking());
            } else {
                ;
            }
            obj.put("data", rank);
        }
//        System.out.println( obj );
        datasets.add(obj);
    }


    data.put("labels", dateLables);
    data.put("datasets", datasets);
}catch (Exception e){
    e.printStackTrace();
}
        System.out.println(data);
        return data;
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


    public String getRGB(int i){
        List<String> arr = new ArrayList<>();
        arr.add("rgb(240, 138, 0)");
        arr.add("rgb(240, 101, 86)");
        arr.add("rgb(117, 214, 86)");
        arr.add("rgb(139, 81, 211)");
        arr.add("rgb(255, 175, 59)");
        //arr.add("rgb(139, 81, 211)");

        return arr.get(i);
    }

}

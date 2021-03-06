package com.braveg.chart;

import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ChartServiceImpl implements ChartService {

    @Autowired
    private ChartMapper chartMapper;

    @Override
    public List<ChartDto> getTodayRankList() throws Exception {
        List<ChartDto> list = null;
        try {
            list = chartMapper.getTodayRankList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<ChartDto> getRankForGraphList(String site) throws Exception {
        List<ChartDto> list = null;
        try {
            list = chartMapper.getRankForGraphList(site);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<ChartDto> getDateRankList() throws Exception {
        List<ChartDto> list = null;
        try {
            list = chartMapper.getDateRankList();
        } catch (Exception e) {
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
    public List<ChartDto> dailyRankVariation(ChartDto dto) throws Exception {
        return chartMapper.dailyRankVariation(dto);
    }

    @Override
    public JSONObject monthlyRankVariation(ChartDto dto) throws Exception {
        List<ChartDto> list = null;
        JSONObject data = new JSONObject();
        try {
            list = chartMapper.monthlyRankVariation(dto);
            List<String> nameArr = new ArrayList<>();
            List<String> dateArr = new ArrayList<>();
            for (ChartDto d : list) {
                nameArr.add(d.getTitle());
                dateArr.add(d.getSdate());
            }

            // TODO
            org.json.simple.JSONArray dateLables = new org.json.simple.JSONArray();

            // ???????????? hashset create
            HashSet<String> hashSet1 = new HashSet<>(nameArr);
            HashSet<String> hashSet2 = new HashSet<>(dateArr);

            // date ???????????? ??? sort
            List<String> dateList = new ArrayList(hashSet2);
            Collections.sort(dateList);
            // ?????? ?????? ??????
            Object[] titles = hashSet1.toArray();

            // set dateLabels
            for (Object q : dateList) {
                dateLables.add(q.toString());
            };

            // TODO arr.size == titles.length
            org.json.simple.JSONArray datasets = new org.json.simple.JSONArray(); // put json object

            for (int i = 0; i < titles.length; i++) {
                // ??? ?????? dataSet
                JSONObject obj = new JSONObject();
                org.json.simple.JSONArray rank = new org.json.simple.JSONArray();
                String x = titles[i].toString();

                for (ChartDto d : list) {
                    if (x.equals(d.getTitle())) {
                        // obj ??? ?????? ?????? ?????? ???
                        if (!obj.containsKey("label")) {
                            obj.put("label", d.getTitle());
                            obj.put("borderColor", getRGB(i)); // TODO ???????????? ?????????
                        }
                        rank.add(d.getRanking());
                    } else {
                        ;
                    }
                    obj.put("data", rank);
                }
                datasets.add(obj);
            }


            data.put("labels", dateLables);
            data.put("datasets", datasets);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }


    /**
     * ///////////////////    Visitor    ///////////////////
     **/

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


    public String getRGB(int i) {
        List<String> arr = new ArrayList<>();
        arr.add("rgb(240, 138, 0)");   // 0
        arr.add("rgb(240, 101, 86)");  // 1
        arr.add("rgb(117, 214, 86)");  // 2
        arr.add("rgb(139, 81, 211)");  // 3
        arr.add("rgb(255, 175, 59)");  // 4
        arr.add("rgb(139, 81, 211)");  // 5
        arr.add("rgb(421, 321, 121)"); // 6
        arr.add("rgb(341, 111, 200)"); // 7
        arr.add("rgb(113, 151, 444)"); // 8
        arr.add("rgb(190, 110, 124)"); // 9

        return arr.get(i);
    }

}

package com.braveg.api.adapters;

import com.braveg.api.RankInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GenieAPI {
    public static List<RankInfo> get() {
        List<RankInfo> result = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String now = sdf.format(new Date());
        String[] o = now.split("-");
        String date = o[0]+o[1]+o[2];
        String hour = o[3];

        String url = "https://www.genie.co.kr/chart/top200?ditc=D&ymd="+date+"&hh="+hour+"&rtm=Y&pg=";
        try{
            result = genieList(url,"1",result);
            result = genieList(url,"2",result);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  result;
    }
    private static List<RankInfo> genieList(String url, String page, List<RankInfo> result) throws Exception{
        Document doc = Jsoup.connect(url + page).get();
        Elements ele = doc.select("div.newest-list");
        Elements tr = ele.select("table.list-wrap tbody tr");
        for (int i = 0; i < tr.size(); i++) {
            Elements td = tr.eq(i).select("td");
            String rank = i+(Integer.parseInt(page)-1)*50+1+"";
            String title = td.eq(4).select("a.title").text();
            String name = td.eq(4).select("a.artist").text();
            //String img = "http:"+td.eq(2).select("a img").attr("src");
            RankInfo ri = new RankInfo();
            ri.setRank(Integer.parseInt(rank));
            ri.setTitle(title);
            ri.setArtist(name);
            result.add(ri);
            ri=null;
        }
        return result;
    }



}

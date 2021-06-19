package com.braveg.api.adapters;

import com.braveg.api.RankInfo;
import org.json.simple.JSONArray;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class BugsAPI {
    public static List<RankInfo> get() {
        List<RankInfo> result = new ArrayList<>();
        try {
            String url = "https://music.bugs.co.kr/chart";

            Document doc = Jsoup.connect(url).get();
            Elements ele = doc.select("div#CHARTrealtime");
            Elements tr = ele.select("table.list.trackList.byChart tbody tr");

            for (int i = 0; i < tr.size(); i++) {
                Elements td = tr.eq(i).select("td");
                String rank = td.eq(1).select("strong").text();
                String title = td.select("input[name='check']").attr("title");
                String name = td.eq(4).select("p.artist a").attr("title");
                //String img = td.eq(2).select("a img").attr("src");

                // set
                RankInfo ri = new RankInfo();
                ri.setRank(i+1);
                ri.setTitle(title);
                ri.setArtist(name);
                result.add(ri);
                ri=null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}

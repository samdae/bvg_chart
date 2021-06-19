package com.braveg.api.adapters;

import com.braveg.api.RankInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class Melon24API {
    public static List<RankInfo> get(){
        List<RankInfo> result = new ArrayList<>();
        try{
            String url = "https://www.melon.com/chart/index.htm";

            Document doc = Jsoup.connect(url).get();
            Elements ele = doc.select("div#tb_list");
            Elements tr = ele.select("table tbody tr");

            for (int i = 0; i < tr.size(); i++) {
                Elements td = tr.eq(i).select("td");
                String title = td.eq(3).select("div.wrap").select("div.wrap_song_info").select("div.ellipsis.rank01").select("span a").text();
                String name = td.eq(3).select("div.wrap").select("div.wrap_song_info").select("div.ellipsis.rank02").select("span a").text();
                //String img = td.eq(1).select("div.wrap a img").attr("src");

                // set
                RankInfo ri = new RankInfo();
                ri.setRank(i+1);
                ri.setTitle(title);
                ri.setArtist(name);
                result.add(ri);
                ri=null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  result;
    }
}

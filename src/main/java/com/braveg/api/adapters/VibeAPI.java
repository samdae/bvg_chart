package com.braveg.api.adapters;

import com.braveg.api.RankInfo;
import com.braveg.utils.DateUtils;
import org.json.XML;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

public class VibeAPI {

    public static List<RankInfo> get() {
        List<RankInfo> result = new ArrayList<>();
        try {
            String url = "https://apis.naver.com/vibeWeb/musicapiweb/vibe/v1/chart/track/total";
            Document doc = Jsoup.connect(url).get();
            org.json.JSONObject json = XML.toJSONObject(doc.toString());
            org.json.JSONObject track = json.getJSONObject("response").getJSONObject("result").getJSONObject("chart").getJSONObject("items").getJSONObject("tracks");
            org.json.JSONArray arr = track.getJSONArray("track"); // 1-100

            for (int i = 0; i < arr.length(); i++) {
                String arrStr = arr.get(i).toString(); // 1-100 중 1줄 단위로 끊어내서

                org.json.simple.JSONObject song = (org.json.simple.JSONObject) new JSONParser().parse(arrStr); // json object로 parsing
                //JSONObject album = (JSONObject) song.get("album");
                org.json.simple.JSONObject art = (org.json.simple.JSONObject) song.get("artists");

                String name = "";
                // 배열
                if (art.get("artist").toString().indexOf("[") > -1) {
                    JSONArray artist = (JSONArray) art.get("artist");
                    for (int j = 0; j < artist.size(); j++) {
                        String str = artist.get(j).toString();
                        org.json.simple.JSONObject atsts = (org.json.simple.JSONObject) new JSONParser().parse(str);
                        name += atsts.get("artistName").toString();
                    }
                } else {
                    org.json.simple.JSONObject artist = (org.json.simple.JSONObject) art.get("artist");
                    name = artist.get("artistName").toString();
                }
                String title = song.get("trackTitle").toString();

                // set
                RankInfo ri = new RankInfo();
                ri.setRank(i + 1);
                ri.setTitle(title);
                ri.setArtist(name);
                ri.setDateTime(DateUtils.getCurrent("yyMMddHH"));
                result.add(ri);
                ri = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}

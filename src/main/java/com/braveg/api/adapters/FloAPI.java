package com.braveg.api.adapters;

import com.braveg.api.RankInfo;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

public class FloAPI {
public static List<RankInfo> get(){
        List<RankInfo> result = new ArrayList<>();
        try{
            String url = "https://www.music-flo.com/api/display/v1/browser/chart/1/track/list?size=100&timestamp="+System.currentTimeMillis();
            Document doc = Jsoup.connect(url)
                    .header("Content-type","application/json")
                    .header("accept", "*/*")
                    .ignoreContentType(true)
                    .get();
            String body = doc.select("body").text();

            JSONParser jsonParse = new JSONParser();
            org.json.simple.JSONObject obj =  (org.json.simple.JSONObject)jsonParse.parse(body);
            org.json.simple.JSONObject data = (org.json.simple.JSONObject) obj.get("data");
            JSONArray list = (JSONArray)data.get("trackList");

            for (int i=0; i<list.size();i++){
                String arrStr = list.get(i).toString();
                org.json.simple.JSONObject arrObj = (org.json.simple.JSONObject) new JSONParser().parse(arrStr);
                org.json.simple.JSONObject artObj = (JSONObject) arrObj.get("representationArtist");

                String name = artObj.get("name").toString();
                String title = arrObj.get("name").toString();
                //JSONObject album = (JSONObject) arrObj.get("album");
                //JSONArray imglist = (JSONArray) album.get("imgList");
                //JSONObject flist = (JSONObject) imglist.get(0);
                //String img = flist.get("url").toString();
                // set
                RankInfo ri = new RankInfo();
                ri.setRank(i + 1);
                ri.setTitle(title);
                ri.setArtist(name);
                result.add(ri);
                ri = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  result;
    }
}

package com.braveg.api.adapters;

import com.braveg.api.RankInfo;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;
import java.util.List;

public class MelonRealAPI {
    public static List<RankInfo> get() {
        List<RankInfo> result = new ArrayList<>();

        try{
            String url = "https://m2.melon.com/cds/chart/mobile2/chartrealtime_listPaging.json?startIndex=1&pageSize=100";

            HttpClient client = HttpClientBuilder.create().build();
            HttpGet req = new HttpGet(url);
            HttpResponse response = client.execute(req);
            ResponseHandler<String> handler = new BasicResponseHandler();
            String body = handler.handleResponse(response);

            JSONParser parser = new JSONParser();
            // json body
            org.json.simple.JSONObject json1 = (org.json.simple.JSONObject) parser.parse(body);
            // list array
            JSONArray array = (JSONArray) parser.parse(json1.get("rowsList").toString());

            for (Object o : array) {
                org.json.simple.JSONObject j = (JSONObject) o;
                String 노래제목 = j.get("SONGNAMEWEBLIST").toString();
                String 가수 = j.get("ARTISTNAMEBASKET").toString();
                String 앨범제목 = j.get("ALBUMNAMEWEBLIST").toString();
                String 순위 = j.get("CURRANK").toString();

                if (노래제목.contains("&#39;")) {
                    노래제목 = 노래제목.replaceAll("&#39;", "`");
                    앨범제목 = 앨범제목.replaceAll("&#39;", "`");
                }
                // set
                RankInfo ri = new RankInfo();
                ri.setRank(Integer.parseInt(순위));
                ri.setTitle(노래제목);
                ri.setArtist(가수);
                result.add(ri);
                ri=null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}

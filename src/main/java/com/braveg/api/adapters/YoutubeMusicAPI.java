package com.braveg.api.adapters;

import com.braveg.api.RankInfo;
import com.braveg.utils.DateUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;
import java.util.List;

public class YoutubeMusicAPI {
    public static List<RankInfo> get(){
        List<RankInfo> result = new ArrayList<>();

        String url = "https://charts.youtube.com/youtubei/v1/browse?alt=json&key=AIzaSyCzEW7JUJdSql0-2V4tHUb6laYm4iAE_dM";
        String entity = "{\"context\":{\"client\":{\"clientName\":\"WEB_REMIX\",\"clientVersion\":\"0.1\",\"hl\":\"ko\",\"gl\":\"KR\",\"experimentIds\":[],\"experimentsToken\":\"\",\"browserName\":\"Safari\",\"deviceMake\":\"apple\",\"browserVersion\":\"14.0.3\",\"osName\":\"Macintosh\",\"osVersion\":\"10_15_6\",\"platform\":\"DESKTOP\",\"utcOffsetMinutes\":540,\"locationInfo\":{\"locationPermissionAuthorizationStatus\":\"LOCATION_PERMISSION_AUTHORIZATION_STATUS_UNSUPPORTED\"},\"musicAppInfo\":{\"musicActivityMasterSwitch\":\"MUSIC_ACTIVITY_MASTER_SWITCH_INDETERMINATE\",\"musicLocationMasterSwitch\":\"MUSIC_LOCATION_MASTER_SWITCH_INDETERMINATE\",\"pwaInstallabilityStatus\":\"PWA_INSTALLABILITY_STATUS_UNKNOWN\"}},\"capabilities\":{},\"request\":{\"internalExperimentFlags\":[],\"sessionIndex\":\"0\"},\"clickTracking\":{\"clickTrackingParams\":\"IhMIqJG5_ND97wIV0ndgCh0pXwWnMghleHRlcm5hbA==\"},\"activePlayers\":{},\"user\":{\"enableSafetyMode\":false}},\"browseId\":\"VLPL4fGSI1pDJn6jXS_Tv_N9B8Z0HTRVJE0m\",\"browseEndpointContextSupportedConfigs\":{\"browseEndpointContextMusicConfig\":{\"pageType\":\"MUSIC_PAGE_TYPE_PLAYLIST\"}}}";

        try {
			HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
			HttpPost postRequest = new HttpPost(url); //POST 메소드 URL 새성
            postRequest.setHeader("Content-Type", "application/json");
            postRequest.setHeader("Accept", "*/*");
            postRequest.setHeader("Connection", "keep-alive");
            postRequest.setHeader("Accept-Encoding", "gzip, deflate, br");
            postRequest.setHeader("Connection", "keep-alive");
            postRequest.setHeader("referer", "https://charts.youtube.com/");

			postRequest.setEntity(new StringEntity(entity)); //json 메시지 입력

			HttpResponse response = client.execute(postRequest);

			//Response 출력
			if (response.getStatusLine().getStatusCode() == 200) {
				ResponseHandler<String> handler = new BasicResponseHandler();
				String body = handler.handleResponse(response);
				JSONParser parser = new JSONParser();

				// 1. 차트 리스트
                org.json.simple.JSONObject json1 = (org.json.simple.JSONObject)parser.parse(body);
                org.json.simple.JSONObject json2 = (org.json.simple.JSONObject)json1.get("contents");
                org.json.simple.JSONObject json3 = (org.json.simple.JSONObject)json2.get("singleColumnBrowseResultsRenderer");

                JSONArray array  = (JSONArray)parser.parse(json3.get("tabs").toString());

                org.json.simple.JSONObject tabs  = (org.json.simple.JSONObject)parser.parse(array.get(0).toString());
                org.json.simple.JSONObject tabs1 = (org.json.simple.JSONObject) tabs.get("tabRenderer");
                org.json.simple.JSONObject tabs2 = (org.json.simple.JSONObject) tabs1.get("content");
                org.json.simple.JSONObject tabs3 = (org.json.simple.JSONObject) tabs2.get("sectionListRenderer");

                JSONArray array2 = (JSONArray) parser.parse(tabs3.get("contents").toString());

                org.json.simple.JSONObject tabs4 = (org.json.simple.JSONObject) parser.parse(array2.get(0).toString());
                org.json.simple.JSONObject tabs5 = (org.json.simple.JSONObject) tabs4.get("musicPlaylistShelfRenderer");

                // Chart Start 1 - 100
                JSONArray chartList = (JSONArray) parser.parse(tabs5.get("contents").toString());

                for( int i=0; i<chartList.size(); i++)
                {
                    org.json.simple.JSONObject chartDetail = (org.json.simple.JSONObject) parser.parse(chartList.get(i).toString());
                    // TODO    Detail Info ( = musicResponsiveListItemRenderer )
                    org.json.simple.JSONObject chartDetail1 = (org.json.simple.JSONObject) chartDetail.get("musicResponsiveListItemRenderer");

                    // TODO    albumInfo => 이 안에 제목, 가수, 앨범명 다 있음
                    JSONArray albumInfo = (JSONArray) parser.parse(chartDetail1.get("flexColumns").toString());

                    // TODO     Get Title Start
                    org.json.simple.JSONObject title1 = (org.json.simple.JSONObject) parser.parse(albumInfo.get(0).toString());
                    org.json.simple.JSONObject title2 = (org.json.simple.JSONObject) title1.get("musicResponsiveListItemFlexColumnRenderer");
                    org.json.simple.JSONObject title3 = (org.json.simple.JSONObject) title2.get("text");
                    JSONArray  title4 = (JSONArray)  parser.parse(title3.get("runs").toString());
                    org.json.simple.JSONObject title5 = (org.json.simple.JSONObject) parser.parse(title4.get(0).toString());
                    String title = title5.get("text").toString();

                    // TODO     Get Name Start
                    org.json.simple.JSONObject name1 = (org.json.simple.JSONObject) parser.parse(albumInfo.get(1).toString());
                    org.json.simple.JSONObject name2 = (org.json.simple.JSONObject) name1.get("musicResponsiveListItemFlexColumnRenderer");
                    org.json.simple.JSONObject name3 = (org.json.simple.JSONObject) name2.get("text");
                    JSONArray  name4 = (JSONArray)  parser.parse(name3.get("runs").toString());
                    org.json.simple.JSONObject name5 = (org.json.simple.JSONObject) parser.parse(name4.get(0).toString());
                    String name = name5.get("text").toString();

                    // set
                    RankInfo ri = new RankInfo();
                    ri.setRank(i+1);
                    ri.setTitle(title);
                    ri.setArtist(name);
                    ri.setDateTime(DateUtils.getCurrent("yyMMddHH"));
                    result.add(ri);
                    ri=null;
                }
			} else {
				System.out.println("response is error : " + response.getStatusLine().getStatusCode());
			}
		} catch (Exception e){
			e.printStackTrace();
		}
        return  result;
    }
}

package com.braveg.chartbatch;

import com.braveg.chart.ChartDto;
import com.braveg.chart.ChartService;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.XML;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sun.jvm.hotspot.runtime.Bytes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Component
public class ChartBatch {

    @Autowired
    ChartService chartService;

    private Logger logger = Logger.getLogger(String.valueOf(ChartBatch.class));

    public List<ChartDto> getList() throws Exception {
        List<ChartDto> list = new ArrayList<>();
        list = this.melon(list);
        list = this.melonRealTime(list);
        list = this.bugs(list);
        list = this.genie(list);
        list = this.flo(list);
        list = this.vibe(list);
        list = this.youtube(list);
        return list;
    }

/**
 * cron config Exam
 * 초(0~59) 분(0~59) 시(0~23) 일(1~31) 월(1~12) 요일(1~7)
 */
// TODO ===========================================================================================
//    @Scheduled(cron = "* * * * * *") // 매일 매시 00분 05초
//    public void test() throws Exception {
//        logger.info("Current Thread : " + Thread.currentThread().getName());
//        System.out.println("============================================");
//        System.out.println("|           B A T C H --- T E S T          |");
//        System.out.println("============================================");
//    }
//    @Scheduled(cron = "5 * * * * *") // 매일 매시 매분 5초
//    public void batch() throws Exception {
//        logger.info("Current Thread : " + Thread.currentThread().getName());
//        List<ChartDto> result = chartService.getTodayRankList();
//        System.out.println(result);
//    }


    @Scheduled(cron = "10 */10 * * * *") // 매일 매시 매10분 간격 10초
    public void eachHourBatch() throws Exception {

        logger.info("Current Thread : " + Thread.currentThread().getName());

        List<ChartDto> list = this.getList();
        ChartDto dto = null;

        for(ChartDto d : list) {
            dto = new ChartDto();
            dto.setSite(  d.getSite()  );
            dto.setTitle( d.getTitle().replace("'","`") ); // mysql 홑따옴표 에러 때문
            dto.setRanking(  d.getRanking()  );

            chartService.insertEachHour(dto);
        }
    }
    @Scheduled(cron = "10 0 0 * * *") // 매일 00시 00분 10초
    public void dateBatch() throws Exception{

        logger.info("Current Thread : " + Thread.currentThread().getName());

        List<ChartDto> list = this.getList();
        ChartDto dto = null;

        for(ChartDto d : list) {
            dto = new ChartDto();
            dto.setSite(  d.getSite()  );
            dto.setTitle( d.getTitle().replace("'","`") ); // mysql 홑따옴표 에러 때문
            dto.setRanking(  d.getRanking()  );

            chartService.insertDate(dto);
        }

    }
    @Scheduled(cron = "0 0 0 * * *") // 매일 00시 00분 00초
    public void resetBatch() throws Exception {
        logger.info("Current Thread : " + Thread.currentThread().getName());
        chartService.resetEachHour();
    }
// TODO ===========================================================================================


    /** MELON 24 Hits */
    public List<ChartDto> melon(List<ChartDto> list) throws Exception {
        String url = "https://www.melon.com/chart/index.htm";

        Document doc = Jsoup.connect(url).get();
        Elements ele = doc.select("div#tb_list");
        Elements tr = ele.select("table tbody tr");

        ChartDto chartDto = null;

        for (int i = 0; i < tr.size(); i++) {
            Elements td = tr.eq(i).select("td");
            String title = td.eq(3).select("div.wrap").select("div.wrap_song_info").select("div.ellipsis.rank01").select("span a").text();
            String name = td.eq(3).select("div.wrap").select("div.wrap_song_info").select("div.ellipsis.rank02").select("span a").text();
            //String img = td.eq(1).select("div.wrap a img").attr("src");

            if (name.indexOf("브레이브") > -1) {
                chartDto = new ChartDto();
                chartDto.setRanking(String.valueOf(i+1));
                chartDto.setTitle(title);
                chartDto.setName(name);
                chartDto.setSite("melon");
                list.add(chartDto);
            }
        }
        return list;
    }

        /** MELON Real Time  */
    public List<ChartDto> melonRealTime(List<ChartDto> list) throws Exception {
        String url = "https://m2.melon.com/cds/chart/mobile2/chartrealtime_listPaging.json?startIndex=1&pageSize=100";

        HttpClient client = HttpClientBuilder.create().build();
        HttpGet req = new HttpGet(url);
        HttpResponse response = client.execute(req);
        ResponseHandler<String> handler = new BasicResponseHandler();
        String body = handler.handleResponse(response);

        JSONParser parser = new JSONParser();
        // json body
        JSONObject json1 = (JSONObject) parser.parse(body);
        // list array
        JSONArray array = (JSONArray) parser.parse(json1.get("rowsList").toString());

        ChartDto chartDto = null;

        for( Object o : array ) {
                JSONObject j = (JSONObject) o;
                String 노래제목 = j.get("SONGNAMEWEBLIST").toString();
                String 가수    = j.get("ARTISTNAMEBASKET").toString();
                String 앨범제목 = j.get("ALBUMNAMEWEBLIST").toString();
                String 순위    = j.get("CURRANK").toString();

                if( 가수.contains("브레이브걸스") ){
                    노래제목 = 노래제목.replaceAll("&#39;", "`");
                    앨범제목 = 앨범제목.replaceAll("&#39;", "`");
                    System.out.println( 순위 + " ==> " + 노래제목 + "  |  " + 가수 + "  |  " + 앨범제목 );

                    chartDto = new ChartDto();
                    chartDto.setRanking(순위);
                    chartDto.setTitle(노래제목);
                    chartDto.setName(가수);
                    chartDto.setSite("melon-r");
                    list.add(chartDto);
                }
            }

        return list;
    }

    /** BUGS */
    public List<ChartDto> bugs(List<ChartDto> list) throws Exception {
        String url = "https://music.bugs.co.kr/chart";

        Document doc = Jsoup.connect(url).get();
        Elements ele = doc.select("div#CHARTrealtime");
        Elements tr = ele.select("table.list.trackList.byChart tbody tr");

        ChartDto chartDto = null;

        for (int i = 0; i < tr.size(); i++) {
            Elements td = tr.eq(i).select("td");
            String rank = td.eq(1).select("strong").text();
            String title = td.select("input[name='check']").attr("title");
            String name = td.eq(4).select("p.artist a").attr("title");
            //String img = td.eq(2).select("a img").attr("src");
            if (name.indexOf("브레이브") > -1) {
                chartDto = new ChartDto();
                chartDto.setRanking(rank);
                chartDto.setTitle(title);
                chartDto.setName(name);
                chartDto.setSite("bugs");
                list.add(chartDto);
            }
        }
        return list;
    }

    /** GENIE */
    public List<ChartDto> genie(List<ChartDto> list) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String now = sdf.format(new Date());
        String[] o = now.split("-");
        String date = o[0]+o[1]+o[2];
        String hour = o[3];

        String url = "https://www.genie.co.kr/chart/top200?ditc=D&ymd="+date+"&hh="+hour+"&rtm=Y&pg=";

        for(int i=1; i<5; i++) {
            list = this.genieList(url, String.valueOf(i), list);
        }

        return list;
    }
    private List<ChartDto> genieList(String url, String page, List<ChartDto> res) throws Exception{
        Document doc = Jsoup.connect(url + page).get();
        Elements ele = doc.select("div.newest-list");
        Elements tr = ele.select("table.list-wrap tbody tr");

        for (int i = 0; i < tr.size(); i++) {
            Elements td = tr.eq(i).select("td");
            String rank = i+(Integer.parseInt(page)-1)*50+1+"";
            String title = td.eq(4).select("a.title").text();
            String name = td.eq(4).select("a.artist").text();
            //String img = "http:"+td.eq(2).select("a img").attr("src");
            ChartDto chartDto = null;
            if (name.contains("브레이브")) {
                chartDto = new ChartDto();
                chartDto.setRanking(rank);
                chartDto.setTitle(title);
                chartDto.setName(name);
                chartDto.setSite("genie");
                res.add(chartDto);
            }
        }
        return res;
    }

    /** FLO  */
    public List<ChartDto> flo(List<ChartDto> array) throws Exception {
        String url = "https://www.music-flo.com/api/display/v1/browser/chart/1/track/list?size=100&timestamp="+System.currentTimeMillis();

        Document doc = Jsoup.connect(url)
                .header("Content-type","application/json")
                .header("accept", "*/*")
                .ignoreContentType(true)
                .get();
        String body = doc.select("body").text();

        JSONParser jsonParse = new JSONParser();
        JSONObject obj =  (JSONObject)jsonParse.parse(body);
        JSONObject data = (JSONObject) obj.get("data");
        JSONArray list = (JSONArray)data.get("trackList");

        ChartDto chartDto = null;

        for (int i=0; i<list.size();i++){
            String arrStr = list.get(i).toString();
            JSONObject arrObj = (JSONObject) new JSONParser().parse(arrStr);
            JSONObject artObj = (JSONObject) arrObj.get("representationArtist");
            String name = artObj.get("name").toString();

            if( name.indexOf("브레이브") > -1){
                //JSONObject album = (JSONObject) arrObj.get("album");
                //JSONArray imglist = (JSONArray) album.get("imgList");
                //JSONObject flist = (JSONObject) imglist.get(0);
                //String img = flist.get("url").toString();
                String rank = i+1+"";
                String title = arrObj.get("name").toString();

                chartDto = new ChartDto();
                chartDto.setRanking(rank);
                chartDto.setTitle(title);
                chartDto.setName(name);
                chartDto.setSite("flo");
                array.add(chartDto);
            }
        }
        return array;
    }

    /** VIBE */
    public List<ChartDto> vibe(List<ChartDto> list)  throws Exception {

        String url = "https://apis.naver.com/vibeWeb/musicapiweb/vibe/v1/chart/track/total";
        Document doc = Jsoup.connect(url).get();
        org.json.JSONObject json = XML.toJSONObject(doc.toString());
        org.json.JSONObject track = json.getJSONObject("response").getJSONObject("result").getJSONObject("chart").getJSONObject("items").getJSONObject("tracks");
        org.json.JSONArray arr = track.getJSONArray("track"); // 1-100

        ChartDto chartDto = null;

        for(int i=0; i<arr.length(); i++) {
            String arrStr = arr.get(i).toString(); // 1-100 중 1줄 단위로 끊어내서

            JSONObject song = (JSONObject) new JSONParser().parse(arrStr); // json object로 parsing
            //JSONObject album = (JSONObject) song.get("album");
            JSONObject art = (JSONObject) song.get("artists");

            String name ="";
            // 배열
            if ( art.get("artist").toString().indexOf("[") > -1 ) {
                JSONArray artist = (JSONArray) art.get("artist");
                for(int j=0; j<artist.size(); j++){
                    String str = artist.get(j).toString();
                    JSONObject atsts = (JSONObject) new JSONParser().parse(str);
                    name +=atsts.get("artistName").toString();
                }
            } else {
                JSONObject artist = (JSONObject) art.get("artist");
                name = artist.get("artistName").toString();
            }

            if( name.indexOf("브레이브") > -1 || name.indexOf("Brave") > -1) {
                String rank = i+1+"";
                String title = song.get("trackTitle").toString();
                //String img = album.get("imageUrl").toString();
                chartDto = new ChartDto();
                chartDto.setRanking(rank);
                chartDto.setTitle(title);
                chartDto.setName(name);
                chartDto.setSite("vibe");
                list.add(chartDto);
            }
        }

        return list;
    }
/*
Youtube Music Entity
{
    "context": {
        "client": {
            "clientName":"WEB_REMIX",
            "clientVersion": 0.1,
            "hl":"ko",
            "gl":"KR",
            "experimentIds": [],
            "experimentsToken": ,
            "browserName":"Safari",
            "deviceMake":"apple",
            "browserVersion": "14.0.3",
            "osName":"Macintosh",
            "osVersion": "10_15_6",
            "platform":"DESKTOP",
            "utcOffsetMinutes": 540,
            "locationInfo": {
                "locationPermissionAuthorizationStatus":"LOCATION_PERMISSION_AUTHORIZATION_STATUS_UNSUPPORTED"
            },
            "musicAppInfo": {
                "musicActivityMasterSwitch":"MUSIC_ACTIVITY_MASTER_SWITCH_INDETERMINATE",
                "musicLocationMasterSwitch":"MUSIC_LOCATION_MASTER_SWITCH_INDETERMINATE",
                "pwaInstallabilityStatus":"PWA_INSTALLABILITY_STATUS_UNKNOWN"
            }
        },
        "capabilities": {},
        "request": {
            "internalExperimentFlags": [],
            "sessionIndex": 0
        },
        "clickTracking": {
            "clickTrackingParams":"IhMIqJG5_ND97wIV0ndgCh0pXwWnMghleHRlcm5hbA=="
        },
        "activePlayers": {},
        "user": {
            "enableSafetyMode": false
        }
    },
    "browseId":"VLPL4fGSI1pDJn6jXS_Tv_N9B8Z0HTRVJE0m",
    "browseEndpointContextSupportedConfigs": {
        "browseEndpointContextMusicConfig": {
            "pageType":"MUSIC_PAGE_TYPE_PLAYLIST"
        }
    }
}
*/
    /** YOUTUBE */
    public List<ChartDto> youtube(List<ChartDto> list)  throws Exception {
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
                JSONObject json1 = (JSONObject)parser.parse(body);
                JSONObject json2 = (JSONObject)json1.get("contents");
                JSONObject json3 = (JSONObject)json2.get("singleColumnBrowseResultsRenderer");

                JSONArray array  = (JSONArray)parser.parse(json3.get("tabs").toString());

                JSONObject tabs  = (JSONObject)parser.parse(array.get(0).toString());
                JSONObject tabs1 = (JSONObject) tabs.get("tabRenderer");
                JSONObject tabs2 = (JSONObject) tabs1.get("content");
                JSONObject tabs3 = (JSONObject) tabs2.get("sectionListRenderer");

                JSONArray array2 = (JSONArray) parser.parse(tabs3.get("contents").toString());

                JSONObject tabs4 = (JSONObject) parser.parse(array2.get(0).toString());
                JSONObject tabs5 = (JSONObject) tabs4.get("musicPlaylistShelfRenderer");

                // Chart Start 1 - 100
                JSONArray chartList = (JSONArray) parser.parse(tabs5.get("contents").toString());

                for( int i=0; i<chartList.size(); i++)
                {
                    JSONObject chartDetail = (JSONObject) parser.parse(chartList.get(i).toString());
                    // TODO    Detail Info ( = musicResponsiveListItemRenderer )
                    JSONObject chartDetail1 = (JSONObject) chartDetail.get("musicResponsiveListItemRenderer");

                    // TODO    albumInfo => 이 안에 제목, 가수, 앨범명 다 있음
                    JSONArray albumInfo = (JSONArray) parser.parse(chartDetail1.get("flexColumns").toString());

                    // TODO     Get Title Start
                    JSONObject title1 = (JSONObject) parser.parse(albumInfo.get(0).toString());
                    JSONObject title2 = (JSONObject) title1.get("musicResponsiveListItemFlexColumnRenderer");
                    JSONObject title3 = (JSONObject) title2.get("text");
                    JSONArray  title4 = (JSONArray)  parser.parse(title3.get("runs").toString());
                    JSONObject title5 = (JSONObject) parser.parse(title4.get(0).toString());
                    String title = title5.get("text").toString();

                    // TODO     Get Name Start
                    JSONObject name1 = (JSONObject) parser.parse(albumInfo.get(1).toString());
                    JSONObject name2 = (JSONObject) name1.get("musicResponsiveListItemFlexColumnRenderer");
                    JSONObject name3 = (JSONObject) name2.get("text");
                    JSONArray  name4 = (JSONArray)  parser.parse(name3.get("runs").toString());
                    JSONObject name5 = (JSONObject) parser.parse(name4.get(0).toString());
                    String name = name5.get("text").toString();

                    if(name.contains("브레이브걸스")){
                        ChartDto chartDto = new ChartDto();
                        chartDto.setRanking( (i+1)+"" );
                        chartDto.setTitle(title);
                        chartDto.setName(name);
                        chartDto.setSite("youtube");
                        list.add(chartDto);
                    }
                }
			} else {
				System.out.println("response is error : " + response.getStatusLine().getStatusCode());
			}
		} catch (Exception e){
			e.printStackTrace();
		}

        return list;
    }





}

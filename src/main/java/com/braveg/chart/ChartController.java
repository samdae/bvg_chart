package com.braveg.chart;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@Slf4j
public class ChartController {

    @Autowired
    ChartService chartService;

    @RequestMapping(value = "/")
    public ModelAndView home(HttpServletRequest request) throws Exception {
        HttpSession session = null;
        try{
            if( request.getSession(false) == null ) {
            log.info(" Session not exist => Create Session! ");

            session = request.getSession(true);
            VisitDto dto = new VisitDto();
            dto.setVisit_agent(request.getHeader("User-Agent"));
            dto.setVisit_ip(request.getRemoteAddr());
            dto.setVisit_refer(request.getHeader("referer"));
            chartService.insertVisitor(dto);
        } else {
            log.info(" Session already exist => Do not create Session! ");
        }
        }catch (Exception e){
            ;
        }finally {
            ModelAndView mav = new ModelAndView();
            mav.setViewName("/index.html");
            return mav;
        }
    }
    @GetMapping(value="/juju/get/today/cnt")
    public @ResponseBody JSONObject getTodayCnt() throws Exception {
        JSONObject json =new JSONObject();
        int cnt = chartService.getTodayCnt();
        json.put("todayCnt", cnt);
        return json;
    }

    @GetMapping(value="/juju/get/total/cnt")
    public @ResponseBody JSONObject getTotalCnt() throws Exception {
        JSONObject json =new JSONObject();
        int cnt = chartService.getTotalCnt();
        json.put("totalCnt", cnt);
        return json;
    }

    @PostMapping("/getChartList.ajax")
    public @ResponseBody JSONObject getChartList() throws Exception {
        JSONObject json = new JSONObject();

        List<ChartDto> list = null;
        list = chartService.getTodayRankList();

        if( list != null && list.size() > 0 ){
            json.put("data", list);
        } else {
            json.put("data", "없다");
        }

        return json;
    }


    @PostMapping("/getRankForGraphList.ajax")
    public @ResponseBody JSONObject getRankForGraphList(HttpServletRequest request) throws Exception {
        String site = request.getParameter("site");

        ChartDto dto = new ChartDto();
        dto.setSite(site);

        JSONObject json = chartService.monthlyRankVariation(dto);

        return json;
    }

    @PostMapping("/getDailyGraphList.ajax")
    public @ResponseBody JSONObject getDailyGraphList(HttpServletRequest request) throws Exception {


        String site = request.getParameter("site");
        String title = request.getParameter("title");

        ChartDto dto = new ChartDto();
        dto.setSite(site);
        dto.setTitle(title);

        JSONObject json = new JSONObject();

        List<ChartDto> list = null;
        list = chartService.dailyRankVariation(dto);

        if( list != null && list.size() > 0 ){
            json.put("data", list);
        } else {
            json.put("data", "null");
        }

        return json;
    }

//    @RequestMapping(value = "/chat")
//    public ModelAndView test() throws Exception {
//        ModelAndView mav = new ModelAndView();
//        mav.setViewName("test");
//        return mav;
//    }



    @ResponseBody
    @RequestMapping("/melon")
    public JSONObject melon() throws Exception {
        String url = "https://www.melon.com/chart/index.htm";
        Document doc = Jsoup.connect(url).get();
        Elements ele = doc.select("div#tb_list");
        Elements tr = ele.select("table tbody tr");

        List<ChartDto> list = new ArrayList<>();
        ChartDto chartDto = null;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < tr.size(); i++) {
            Elements td = tr.eq(i).select("td");
            String title = td.eq(3).select("div.wrap").select("div.wrap_song_info").select("div.ellipsis.rank01").select("span a").text();
            String name = td.eq(3).select("div.wrap").select("div.wrap_song_info").select("div.ellipsis.rank02").select("span a").text();
            String img = td.eq(1).select("div.wrap a img").attr("src");

            if (name.indexOf("브레이브") > -1) {
                chartDto = new ChartDto();
                chartDto.setRanking(String.valueOf(i+1));
                chartDto.setTitle(title);
                chartDto.setName(name);
                chartDto.setImg(img);
                list.add(chartDto);
            }
        }
        JSONObject json = new JSONObject();
        json.put("melon", list);
        return json;
    }

    @ResponseBody
    @RequestMapping("/bugs")
    public JSONObject bugs() throws Exception {
        String url = "https://music.bugs.co.kr/chart";
        Document doc = Jsoup.connect(url).get();
        Elements ele = doc.select("div#CHARTrealtime");
        Elements tr = ele.select("table.list.trackList.byChart tbody tr");
        List<ChartDto> list = new ArrayList<>();
        ChartDto chartDto = null;
        for (int i = 0; i < tr.size(); i++) {
            Elements td = tr.eq(i).select("td");
            String rank = td.eq(1).select("strong").text();
            String title = td.select("input[name='check']").attr("title");
            String name = td.eq(4).select("p.artist a").attr("title");
            String img = td.eq(2).select("a img").attr("src");
            if (name.indexOf("브레이브") > -1) {
                chartDto = new ChartDto();
                chartDto.setRanking(rank);
                chartDto.setTitle(title);
                chartDto.setName(name);
                chartDto.setImg(img);
                list.add(chartDto);

            }
        }
        JSONObject json = new JSONObject();
        json.put("bugs", list);
        return json;
    }

    public List<ChartDto> genieList(String url, String page, List<ChartDto> res) throws Exception{
        Document doc = Jsoup.connect(url + page).get();
        Elements ele = doc.select("div.newest-list");
        Elements tr = ele.select("table.list-wrap tbody tr");

        for (int i = 0; i < tr.size(); i++) {
            Elements td = tr.eq(i).select("td");
            String rank = i+(Integer.parseInt(page)-1)*50+1+"";
            String title = td.eq(4).select("a.title").text();
            String name = td.eq(4).select("a.artist").text();
            String img = "http:"+td.eq(2).select("a img").attr("src");
            if (name.contains("브레이브")) {
                ChartDto chartDto = new ChartDto();
                chartDto.setRanking(rank);
                chartDto.setTitle(title);
                chartDto.setName(name);
                chartDto.setImg(img);
                res.add(chartDto);
            }
        }
        return res;
    }

    @ResponseBody
    @RequestMapping("/genie")
    public JSONObject genie() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String now = sdf.format(new Date());
        String[] o = now.split("-");
        String date = o[0]+o[1]+o[2];
        String hour = o[3];

        String url = "https://www.genie.co.kr/chart/top200?ditc=D&ymd="+date+"&hh="+hour+"&rtm=Y&pg=";
        List<ChartDto> list = new ArrayList<>();
        for(int i=1; i<5; i++) {
            list = this.genieList(url, String.valueOf(i), list);
        }

        JSONObject json = new JSONObject();
        json.put("genie", list);
        return json;
    }

    @ResponseBody
    @RequestMapping("/flo")
    public JSONObject flo() throws Exception {
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

        List<ChartDto> list1 = new ArrayList<>();
        ChartDto chartDto = null;

        for (int i=0; i<list.size();i++){
            String arrStr = list.get(i).toString();
            JSONObject arrObj = (JSONObject) new JSONParser().parse(arrStr);
            JSONObject artObj = (JSONObject) arrObj.get("representationArtist");
            String name = artObj.get("name").toString();

            if( name.indexOf("브레이브") > -1){
                JSONObject album = (JSONObject) arrObj.get("album");
                JSONArray imglist = (JSONArray) album.get("imgList");
                JSONObject flist = (JSONObject) imglist.get(0);

                String rank = i+1+"";
                String img = flist.get("url").toString();
                String title = arrObj.get("name").toString();

                chartDto = new ChartDto();
                chartDto.setRanking(rank);
                chartDto.setTitle(title);
                chartDto.setName(name);
                chartDto.setImg(img);
                list1.add(chartDto);
            }
        }
        JSONObject json = new JSONObject();
        json.put("flo", list1);
        return json;
    }


    @ResponseBody
    @RequestMapping("/vibe")
    public JSONObject vibe()  throws Exception {

        String url = "https://apis.naver.com/vibeWeb/musicapiweb/vibe/v1/chart/track/total";
        Document doc = Jsoup.connect(url).get();
        org.json.JSONObject json = XML.toJSONObject(doc.toString());
        org.json.JSONObject track = json.getJSONObject("response").getJSONObject("result").getJSONObject("chart").getJSONObject("items").getJSONObject("tracks");
        org.json.JSONArray arr = track.getJSONArray("track"); // 1-100

        List<ChartDto> list = new ArrayList<>();
        ChartDto chartDto = null;
        for(int i=0; i<arr.length(); i++) {
            String arrStr = arr.get(i).toString(); // 1-100 중 1줄 단위로 끊어내서

            JSONObject song = (JSONObject) new JSONParser().parse(arrStr); // json object로 parsing
            JSONObject album = (JSONObject) song.get("album");
            JSONObject art = (JSONObject) song.get("artists"); //

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
                String img = album.get("imageUrl").toString();
                chartDto = new ChartDto();
                chartDto.setRanking(rank);
                chartDto.setTitle(title);
                chartDto.setName(name);
                chartDto.setImg(img);
                list.add(chartDto);
//                System.out.println(rank + " - " + title + " - " + name + " - " + img);
            }
        }

        JSONObject result = new JSONObject();
        result.put("vibe", list);

        return result;
    }


    @ResponseBody
    @RequestMapping("/youtube")
    public JSONObject youtube()  throws Exception {
        JSONObject json = new JSONObject();
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
                List<ChartDto> list = new ArrayList<>();

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
                    JSONArray  title4 = (JSONArray) parser.parse(title3.get("runs").toString());
                    JSONObject title5 =(JSONObject) parser.parse(title4.get(0).toString());
                    String title = title5.get("text").toString();

                    // TODO     Get Name Start
                    JSONObject name1 = (JSONObject) parser.parse(albumInfo.get(1).toString());
                    JSONObject name2 = (JSONObject) name1.get("musicResponsiveListItemFlexColumnRenderer");
                    JSONObject name3 = (JSONObject) name2.get("text");
                    JSONArray  name4 = (JSONArray) parser.parse(name3.get("runs").toString());
                    JSONObject name5 =(JSONObject) parser.parse(name4.get(0).toString());
                    String name = name5.get("text").toString();

                    if(name.contains("브레이브걸스")){
                        ChartDto chartDto = new ChartDto();
                        chartDto.setRanking( (i+1)+"" );
                        chartDto.setTitle(title);
                        chartDto.setName(name);
                        list.add(chartDto);
                        //System.out.println((i+1) + "==>   : " + title + "            " + name);
                    }
                }
                json.put("youtube", list);
			} else {
				System.out.println("response is error : " + response.getStatusLine().getStatusCode());
			}
		} catch (Exception e){
			e.printStackTrace();
		}

        return json;
    }

}
package com.braveg.api;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestController
public class ApiController {

    private ApiAdapters apiAdapters;
    private static List site = Arrays.asList("melon24" , "melonreal" , "youtubemusic" , "bugs" , "genie" , "flo" , "vibe");

    @Autowired
    public ApiController(ApiAdapters apiAdapters){
        this.apiAdapters = apiAdapters;
    }

    private List<String> validator(String kindsName){
        // to lower case & trim
        kindsName = kindsName.toLowerCase(Locale.ROOT).replaceAll(" ","");
        // all 이면 모두 지정
        kindsName = kindsName.contains("all") ? "melon24,melonreal,youtubemusic,bugs,genie,flo,vibe" : kindsName;
        // make site name list & distinct
        return Arrays.asList(kindsName.split(","))
                .stream()
                .distinct() // 중복제거
                .filter( t -> site.contains(t) ) // 개짓거리 방지
                .collect(Collectors.toList());
    }

/*
/this-api :
{
    "about" : {
        "apiName" : "음원사이트 별 실시간 차트 내용 API",
        "productBy" : "DH",
        "QnALink": "https://open.kakao.com/o/s41Pxd7c",
        "kinds" : ["all", "melon24", "melonreal", "youtubemusic", "bugs", "genie", "flo", "vibe"],
        "specifications" : {
            "method" : "GET",
            "produces" : "application/json",
            "consumes" : "any"
        },
        "methods" : [
            {
                "reqeustMethod-0" : "https://xn--369a72t8xeba599ce3h.com/api/{kindsName}",
                "reqeustMethod-0-introduce": {
                        "introduce" : "[kinds]의 내용 중 원하는 N개의 음원 사이트를 골라 각 사이트의 1~100위의 실시간 차트 내용을 반환한다.",
                        "kindsName" : "요청할 음원사이트 명. [kinds]에 있는 내용"
                }
            },
            {
                "reqeustMethod-1" : "https://xn--369a72t8xeba599ce3h.com/api/{kindsName}/{startRank}/{endRank}",
                "reqeustMethod-1-introduce": {
                        "introduce" : "[kinds]의 내용 중 원하는 N개의 음원 사이트를 골라 각 사이트의 startRank ~ endRank 까지의 실시간 차트 내용을 반환한다.",
                        "kindsName" : "요청할 음원사이트 명. [kinds]에 있는 내용(1. 구분은 ','된다. 2. 'all' 이 들어가있다면 항상 모든 사이트의 결과를 반환한다. )",
                        "startRank" : "요청할 차트의 시작 순위 (최소 1)",
                        "endRank" : "요청할 차트의 마지막 순위 (최대 100)"
                }
            },
            {
                "requestMethod-2" : "https://xn--369a72t8xeba599ce3h.com/api/{kindsName}/title/{titleName}",
                "reqeustMethod-2-introduce": {
                    "introduce" : "[kinds]의 내용 중 원하는 N개의 음원 사이트를 골라 각 사이트의 1~100위의 실시간 차트 내용 중 titleName(곡명)의 실시간 순위를 반환한다.",
                    "kindsName" : "요청할 음원사이트 명. [kinds]에 있는 내용",
                    "songName" : "노래제목"
                }
            },
            {
                "requestMethod-3" : "https://xn--369a72t8xeba599ce3h.com/api/{kindsName}/artist/{artistName}",
                "reqeustMethod-3-introduce": {
                    "introduce" : "[kinds]의 내용 중 원하는 N개의 음원 사이트를 골라 각 사이트의 1~100위의 실시간 차트 내용 중 artistName(아티스트의 모든 곡)에 대한 순위를 반환한다.",
                    "kindsName" : "요청할 음원사이트 명. [kinds]에 있는 내용",
                    "songName" : "노래제목"
                }
            }
        ]
    }
}
*/

    /**
     * Introduce this API
     */
    @GetMapping(value="/this-api", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity method() throws Exception {
        String jsonStr = "{\"about\" : {\"apiName\" : \"음원사이트 별 실시간 차트 내용 API\",\"productBy\" : \"DH\",\"QnALink\": \"https://open.kakao.com/o/s41Pxd7c\", \"kinds\" : [\"all\", \"melon24\", \"melonreal\", \"youtubemusic\", \"bugs\", \"genie\", \"flo\", \"vibe\"], \"specifications\" : {\"method\" : \"GET\",\"produces\" : \"application/json\",\"consumes\" : \"any\"},\"methods\" : [{\"reqeustMethod-0\" : \"https://xn--369a72t8xeba599ce3h.com/api/{kindsName}\", \"reqeustMethod-0-introduce\": {\"introduce\" : \"[kinds]의 내용 중 원하는 N개의 음원 사이트를 골라 각 사이트의 1~100위의 실시간 차트 내용을 반환한다.\", \"kindsName\" : \"요청할 음원사이트 명. [kinds]에 있는 내용\"}},{\"reqeustMethod-1\" : \"https://xn--369a72t8xeba599ce3h.com/api/{kindsName}/{startRank}/{endRank}\", \"reqeustMethod-1-introduce\": {\"introduce\" : \"[kinds]의 내용 중 원하는 N개의 음원 사이트를 골라 각 사이트의 startRank ~ endRank 까지의 실시간 차트 내용을 반환한다.\", \"kindsName\" : \"요청할 음원사이트 명. [kinds]에 있는 내용(1. 구분은 ','된다. 2. 'all' 이 들어가있다면 항상 모든 사이트의 결과를 반환한다. )\", \"startRank\" : \"요청할 차트의 시작 순위 (최소 1)\",\"endRank\" : \"요청할 차트의 마지막 순위 (최대 100)\"}},{\"requestMethod-2\" : \"https://xn--369a72t8xeba599ce3h.com/api/{kindsName}/title/{titleName}\", \"reqeustMethod-2-introduce\": {\"introduce\" : \"[kinds]의 내용 중 원하는 N개의 음원 사이트를 골라 각 사이트의 1~100위의 실시간 차트 내용 중 titleName(곡명)의 실시간 순위를 반환한다.\", \"kindsName\" : \"요청할 음원사이트 명. [kinds]에 있는 내용\", \"songName\" : \"노래제목\"}},{\"requestMethod-3\" : \"https://xn--369a72t8xeba599ce3h.com/api/{kindsName}/artist/{artistName}\", \"reqeustMethod-3-introduce\": {\"introduce\" : \"[kinds]의 내용 중 원하는 N개의 음원 사이트를 골라 각 사이트의 1~100위의 실시간 차트 내용 중 artistName(아티스트의 모든 곡)에 대한 순위를 반환한다.\", \"kindsName\" : \"요청할 음원사이트 명. [kinds]에 있는 내용\", \"songName\" : \"노래제목\"}}]}}";
        JSONParser parser = new JSONParser();
        JSONObject result = (JSONObject) parser.parse(jsonStr);
        return ResponseEntity.ok(result);
    }
    /**
     * {kindsName}'s 1 ~ 100 get ChartList
     */
    @GetMapping(value = "/api/{kindsName}", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity requestMethod_0(@PathVariable(value="kindsName") String kindsName) {
        // initialize result body
        JSONObject resultBody = new JSONObject();
        // initialize result list
        List<JSONObject> resultList = new ArrayList<>();

        // get validated list
        List<String> siteList = this.validator(kindsName);
        // roof and append list
        for(String name : siteList){
            JSONObject eachSiteObj = new JSONObject();
            eachSiteObj.put(name, apiAdapters.separator(name));
            resultList.add(eachSiteObj);
        }
        resultBody.put("request", siteList);
        resultBody.put("body",resultList);
        return ResponseEntity.ok(resultBody);
    }

    /**
     * {kindsName}'s {startRank} ~ {endRank} get ChartList
     */
    @GetMapping(value = "/api/{kindsName}/{startRank}/{endRank}",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity requestMethod_1(@PathVariable(value="kindsName") String kindsName,
                                          @PathVariable(value="startRank") @Min(1) Integer startRank,
                                          @PathVariable(value="endRank") @Min(100) Integer endRank) {

        if( startRank > endRank ) {
            JSONObject badReq = new JSONObject();
            badReq.put("descriptive","https://xn--369a72t8xeba599ce3h.com/this-api");
            badReq.put("message","400 Bad Request (startRank의 값이 endRank의 값보다 작아야합니다.)");
            return ResponseEntity.badRequest().body(badReq);
        }

        // initialize result body
        JSONObject resultBody = new JSONObject();
        // initialize result list
        List<JSONObject> resultList = new ArrayList<>();

        // get validated list
        List<String> siteList = this.validator(kindsName);
        // roof and append list
        for(String name : siteList){
            JSONObject eachSiteObj = new JSONObject();
            eachSiteObj.put(
                    name, apiAdapters.rankPicker(apiAdapters.separator(name)
                                                , startRank
                                                , endRank)
            );
            resultList.add(eachSiteObj);
        }
        resultBody.put("request", siteList);
        resultBody.put("body",resultList);
        return ResponseEntity.ok(resultBody);
    }

    /**
     * search {titleName} in {kindsName}'s 1~100 ChartLIst
     */
    @GetMapping(value = "/api/{kindsName}/title/{titleName}",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity requestMethod_2(@PathVariable(value="kindsName") String kindsName,
                                          @PathVariable(value="titleName") String titleName) {
        // initialize result body
        JSONObject resultBody = new JSONObject();
        // initialize result list
        List<JSONObject> resultList = new ArrayList<>();

        // get validated list
        List<String> siteList = this.validator(kindsName);
        // roof and append list
        for(String name : siteList){
            JSONObject eachSiteObj = new JSONObject();
            eachSiteObj.put(
                    name, apiAdapters.songPicker(apiAdapters.separator(name), titleName)
            );
            resultList.add(eachSiteObj);
        }
        resultBody.put("request", siteList);
        resultBody.put("body",resultList);
        return ResponseEntity.ok(resultBody);
    }

    /**
     * search {artistName} in {kindsName}'s 1~100 ChartLIst
     */
    @GetMapping(value = "/api/{kindsName}/artist/{artistName}",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity requestMethod_3(@PathVariable(value="kindsName") String kindsName,
                                          @PathVariable(value="artistName") String artistName ) {

        // initialize result body
        JSONObject resultBody = new JSONObject();
        // initialize result list
        List<JSONObject> resultList = new ArrayList<>();

        // get validated list
        List<String> siteList = this.validator(kindsName);
        // roof and append list
        for(String name : siteList){
            JSONObject eachSiteObj = new JSONObject();
            eachSiteObj.put(
                    name, apiAdapters.artistPicker(apiAdapters.separator(name), artistName)
            );
            resultList.add(eachSiteObj);
        }
        resultBody.put("request", siteList);
        resultBody.put("body",resultList);
        return ResponseEntity.ok(resultBody);
    }

}










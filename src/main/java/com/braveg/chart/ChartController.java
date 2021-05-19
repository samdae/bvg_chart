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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin()
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




}

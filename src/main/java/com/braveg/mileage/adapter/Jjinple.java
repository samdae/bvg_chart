package com.braveg.mileage.adapter;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
@CrossOrigin()
@Slf4j
public class Jjinple {

    public Jjinple() {
        // TODO Constructor

    }

    public static String Jjinple( String id, String pw ) throws Exception {
        log.info("==========   JJinple   ==========");

        // TODO - Set Params
        Map<String, String> params = new HashMap<>();
        params.put("loginPath"  , "http://www.jjinpl.com/");
        params.put("tempLogin"  , "1"                     );
        params.put("captchaType", "0"                     );
        params.put("user_id"    , id                      );
        params.put("password"   , pw                      );

        // TODO - Set Headers
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type"      , "multipart/form-data;"     );
        headers.put("User-Agent"        , "PostmanRuntime/7.26.10"   );
        headers.put("Accept"            , "*/*"                      );
        headers.put("Accept-Encoding"   , "gzip, deflate, br"        );
        headers.put("Connection"        , "keep-alive"               );

        String result = "";

        try {

            Connection.Response loginResponse = Jsoup
                    .connect("http://www.jjinpl.com/login/login_check.php")
                    .data(params)
                    .headers(headers)
                    .method(Connection.Method.POST)
                    .execute();

            Document doc = Jsoup.connect("http://www.jjinpl.com/member/index.php")
                    .cookies(loginResponse.cookies()).timeout(3000000).get();

            Elements els = doc.select("#mypageIndexPage .baseInfo .infoGroup2 tbody tr").eq(4);
            //String test = doc.select("#mypageIndexPage .baseInfo .infoGroup2 tbody").text();

            result = els.text().replaceAll("[^0-9]","");

        } catch ( IOException e ) {
            result = "ID/PW 오류 또는 통신오류";
            e.printStackTrace();
        }

        log.info("JJINPLE RESULT ==> {}", result);
System.out.println("JJINPLE RESULT ==> "+ result);
        return result;

    }

}

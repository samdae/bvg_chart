package com.braveg.mileage.adapter;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
@CrossOrigin()
@Slf4j
public class FileSun {
    public FileSun() {
        // TODO Constructor

    }

    public static String filesun ( String id, String pw ) throws Exception {
        log.info("==========   FileSun   ==========");

        // TODO - Set Parmas
        Map<String, String> params = new HashMap<>();
        params.put("user_id"    , id    );
        params.put("password"   , pw    );
        params.put("s_url"      , "https://www.filesun.com" );
        params.put("captchaType", "0"   );
        // TODO - Set Headers
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type","multipart/form-data;" );
        headers.put("User-Agent","PostmanRuntime/7.26.10" );
        headers.put("Accept","*/*" );
        headers.put("Accept-Encoding","gzip, deflate, br" );
        headers.put("Connection","keep-alive" );

        String result = "";

        try {

            Connection.Response loginResponse = Jsoup
                    .connect("https://www.filesun.com/login/login_check.php")
                    .data(params)
                    .headers(headers)
                    .method(Connection.Method.POST)
                    .execute();

            Document doc = Jsoup.connect("https://www.filesun.com")
                    .cookies(loginResponse.cookies()).timeout(3000000).get();

            String test = doc.select("#leftLogin .member .info .list .mileage .td").text();

            // TODO 마일리지
            System.out.println( test.replaceAll("[^0-9]","") );

            result = test.replaceAll("[^0-9]","");

        } catch ( IOException e ) {
            result = "ID/PW 오류 또는 통신오류";
            e.printStackTrace();
        }

        log.info("FILE SUN RESULT ==> {}", result);
System.out.println("FILE SUN RESULT ==> "+ result);
        return result;
    }

}

package com.braveg.mileage.adapter;

import com.braveg.mileage.adapter.ssl.SSLConfig;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.HashMap;
import java.util.Map;
@CrossOrigin()
@Slf4j
public class PdPop extends SSLConfig {

    public PdPop() {
        // TODO Constructor

    }

    public static String pdpop ( String id, String pw ) throws Exception {
        log.info("==========   PdPop   ==========");

        // TODO Login Process URL -
        String loginUrl = "http://member.pdpop.com/member_re.php";

        // TODO Set Configuration
        String mode   = "login";
        String chkVal = "left";
        //String domain = "pdpop.com";
        String domain = "bondisk.com";
//        String domain = site;

        // TODO Set Parmas
        Map<String, String> params = new HashMap<>();
        params.put("mode"   , mode  );
        params.put("chkVal" , chkVal);
        params.put("id"     , id    );
        params.put("domain" , domain);
        params.put("passwd" , pw    );

        String result = "";

        try {

            setSSL();
            Connection.Response loginResponse = Jsoup
                    .connect(loginUrl)
                    .data(params)
                    .method(Connection.Method.POST)
                    .execute();

            System.out.println("PD POP");
            System.out.println(loginResponse.body());

            Document doc = Jsoup.connect("https://member.pdpop.com/login/left_login_main_re.html?url=http%3A%2F%2Fwww.pdpop.com")
                    .cookies(loginResponse.cookies()).timeout(3000000)
                    .get();

            String test = doc.select(".infobox li").eq(2).select(".pinfo").text();

            System.out.println(test.replaceAll("[^0-9]", ""));

            result = test.replaceAll("[^0-9]", "");

        } catch ( Exception e ) {
            result = "ID/PW 오류 또는 통신오류";
            e.printStackTrace();
        }

        log.info("PD POP RESULT ==> {}",result);
System.out.println("PD POP RESULT ==> "+ result);
        return result;
    }


}

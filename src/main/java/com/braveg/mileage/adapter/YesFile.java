package com.braveg.mileage.adapter;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class YesFile {

    public YesFile() {
        // TODO Constructor
    }

    public static String yesfile ( String id, String pw ) throws Exception {
        log.info("==========   YesFile   ==========");

        // TODO - Getting Component - ` LOGIN KEY `
        Document docs = Jsoup.connect("http://www.yesfile.com").get();
        Elements els = docs.select("script");

        String LOGIN_KEY = "";

        for (Element a : els) {
            String node = a.toString();
            if (node.contains("LOGIN_KEY")) {
                int x = node.indexOf("var LOGIN_KEY = \"");
                int y = node.indexOf("\" ;");

                LOGIN_KEY = node.substring(x + 17, y);
            }
        }

        // TODO - Set Parameters
        Map<String, String> params = new HashMap<>();
        params.put("pg_mode"    , "login"   );
        params.put("new_home"   , "yes"     );
        params.put("go_url"     , "/"       );
        params.put("userid"     , id        );
        params.put("userpw"     , pw        );
        params.put("userid_save", "1"       );
        params.put("login_key"  , LOGIN_KEY );

        // TODO - Set Headers
        Map<String, String> headers = new HashMap<>();
        headers.put("User-Agent"     , "PostmanRuntime/7.26.10" );
        headers.put("Accept"         , "*/*"                    );
        headers.put("Accept-Encoding", "gzip, deflate, br"      );
        headers.put("Connection"     , "keep-alive"             );

        String result = "";

        try {

            Connection.Response loginResponse = Jsoup
                    .connect("http://www.yesfile.com/login/index.php")
                    .data(params)
                    .headers(headers)
                    .method(Connection.Method.POST)
                    .execute();

            log.info("YES FILE Response Headers ==> {}", loginResponse.headers() );
            log.info("YES FILE Response Body ==> {}", loginResponse.body() );

            Document doc = Jsoup.connect("https://www.yesfile.com/")
                    .cookies(loginResponse.cookies()).timeout(3000000).get();

            String test = doc.select("#left .login_wrap2 .inBox .point tr").eq(4).text();
            System.out.println( test.replaceAll("[^0-9]","") );

            result = test.replaceAll("[^0-9]","");

        } catch ( IOException e ) {
            result = "ID/PW 오류 또는 통신오류";
            e.printStackTrace();
        }

        log.info("YES FILE RESULT ==> {}",result);
System.out.println("YES FILE  RESULT ==> "+ result);
        return result;

    }



}

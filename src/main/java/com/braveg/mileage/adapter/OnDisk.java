package com.braveg.mileage.adapter;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.HashMap;
import java.util.Map;
@CrossOrigin()
@Slf4j
public class OnDisk {

    public OnDisk(){
        // TODO Constructor
    }


    public static String ondisk ( String id, String pw ) throws Exception {
        log.info("==========   OnDisk   ==========");

        // TODO - Set Headers
        Map<String, String> headers = new HashMap<>();
        headers.put("Host"           , "m.ondisk.co.kr"                                     );
        headers.put("Connection"     , "keep-alive"                                         );
        headers.put("Content-Type"   , "application/x-www-form-urlencoded; charset=UTF-8"   );
        headers.put("Origin"         , "https://m.ondisk.co.kr"                             );
        headers.put("Content-Type"   , "application/x-www-form-urlencoded"                  );
        headers.put("Accept-Encoding", "gzip, deflate, br"                                  );
        headers.put("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7"                );

        // TODO - Set Params
        Map<String, String> params = new HashMap<>();
        params.put("mode"       , "login_prc");
        params.put("referrer"   , "/"        );
        params.put("auto_login" , "Y"        );
        params.put("mb_id"      , id         );
        params.put("mb_pw"      , pw         );

        String result = "";

        try{

            Connection.Response loginResponse = Jsoup
                    .connect("https://m.ondisk.co.kr/api/member/login_prc.php")
                    .data(params)
                    .headers(headers)
                    .method(Connection.Method.POST)
                    .execute();

            System.out.println( loginResponse.body() );
            Document doc = Jsoup.connect("https://ondisk.co.kr/index.php")
                    .cookies(loginResponse.cookies()).timeout(3000000).get();

            result = doc.select("#page-loginInfo .list .ctrl-ticket").text()
                    .replaceAll("[^0-9]","");

            System.out.println( result );

        } catch ( Exception e ) {
            result = "ID/PW 오류 또는 통신오류";
            e.printStackTrace();
        }

        log.info("ONDISK RESULT ==> {}",result);
System.out.println("ONDISK RESULT ==> "+ result);
        return result;

    }


}

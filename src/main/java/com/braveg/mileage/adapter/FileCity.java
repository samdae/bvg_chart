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
public class FileCity {

    public FileCity() {
        // TODO Constructor

    }

    public static String filecity ( String id, String pw ) throws Exception {
        log.info("==========   FileCity   ==========");

        // TODO - Set Login Proc URL
        String loginUrl = "https://www.filecity.co.kr/module/member.php";

        // TODO - Set Login Proc Params
        Map<String, String> params = new HashMap<>();
        params.put("device_model"   , ""        );
        params.put("device_vendor"  , ""        );
        params.put("type"           , "login"   );
        params.put("userid"         , id        );
        params.put("unity"          , ""        );
        params.put("userpw"         , pw        );

        String result = "";

        try {

            Connection.Response loginResponse = Jsoup
                    .connect(loginUrl)
                    .data(params)
                    .method(Connection.Method.POST)
                    .execute();

            log.info("FILE CITY Response Headers ==> {}", loginResponse.headers());
            log.info("FILE CITY Response Bodys ==> {}", loginResponse.body());

            Document doc = Jsoup.connect("https://www.filecity.co.kr/")
                    .cookies(loginResponse.cookies()).timeout(3000000)
                    .get();

            result = doc.select("#login_reload .login_top .user_point_info .info_point ul")
                    .eq(5).select("li")
                    .eq(2).select("span")
                    .text().replaceAll("[^0-9]","");

        } catch ( Exception e ) {
            result = "ID/PW 오류 또는 통신오류";
            e.printStackTrace();
        }

        log.info("FILE CITY RESULT ==> {}", result);
        System.out.println("FILE CITY RESULT ==> "+ result);

        return result;

    }
}

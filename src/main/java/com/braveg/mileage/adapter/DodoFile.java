package com.braveg.mileage.adapter;

import com.braveg.mileage.adapter.ssl.SSLConfig;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.HashMap;
import java.util.Map;
@CrossOrigin()
@Slf4j
public class DodoFile extends SSLConfig {
    public DodoFile() {
        // TODO Constructor

    }

    public static String dodofile ( String id, String pw ) throws Exception {
        log.info("==========   DodoFile   ==========");

        Document init = Jsoup.connect("http://www.dodofile.com/").get();
        Elements form = init.select("form[name='loginFrm']");

        String secure   = form.select("input[name='secure']"  ).val();
        String adtme    = form.select("input[name='adtme']"   ).val();
        String httpsurl = form.select("input[name='httpsurl']").val();
        String httpurl  = form.select("input[name='httpurl']" ).val();
        String snlogin  = form.select("input[name='sSiteNameLogin']").val();
        String renew    = form.select("input[name='renew']"   ).val();
        String parrot   = form.select("input[name='parrot']"  ).val();

        Map<String, String> params = new HashMap<>();
        params.put("secure"     , secure    );
        params.put("adtme"      , adtme     );
        params.put("httpsurl"   , httpsurl  );
        params.put("httpurl"    , httpurl   );
        params.put("sSiteNameLogin", snlogin);
        params.put("renew"      , renew     );
        params.put("parrot"     , parrot    );
        params.put("log_save"   , "Y"       );
        params.put("mb_id"      , id        );
        params.put("mb_pw"      , pw        );

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type"      , "multipart/form-data;"        );
        headers.put("Accept"            , "*/*"                         );
        headers.put("Accept-Encoding"   , "gzip, deflate, br"           );
        headers.put("Connection"        , "keep-alive"                  );
        headers.put("Origin"            , "https://www.dodofile.com/"   );
        headers.put("Referer"           , "https://www.dodofile.com/"   );

        String result = "";
        try{

            setSSL();

            Connection.Response loginResponse = Jsoup
                    .connect("http://www.dodofile.com/models/common/main/login/loginPrc_ssl.php")
                    .data(params)
                    .headers(headers)
                    .method(Connection.Method.POST)
                    .execute();

            log.info("DODO FILE Response Headers ==> {}", loginResponse.headers());
            log.info("DODO FILE Response Bodys ==> {}", loginResponse.body());

            Document doc = Jsoup.connect("http://www.dodofile.com/")
                    .cookies(loginResponse.cookies()).timeout(3000000).get();

            result = doc.select(".login_box .login_box_cash ul").eq(2)
                    .select("li").eq(1).select("span")
                    .text().replaceAll("[^0-9]","");
        } catch ( Exception e ) {
            result = "ID/PW 오류 또는 통신오류";
            e.printStackTrace();
        }

        log.info("DODO FILE RESULT ==> {}", result);
        System.out.println("DODO FILE RESULT ==> "+ result);
        return result;
    }
}

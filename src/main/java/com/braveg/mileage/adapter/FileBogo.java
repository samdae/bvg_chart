package com.braveg.mileage.adapter;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@CrossOrigin()
public class FileBogo {

    public FileBogo () throws Exception {
        // TODO Constructor
    }

    public static String filebogo ( String id, String pw ) throws Exception {
        log.info("==========   FileBogo   ==========");

        /**
         * Get Hashing Token key
         */

        // TODO - Client Create & Build
        HttpClient client = HttpClientBuilder.create().build();

        // TODO Set Headers - tk
        HttpPost postRequest = new HttpPost("https://ssl.filebogo.com/ajax_controller.php");
        postRequest.setHeader("Content-Type"    ,  "application/x-www-form-urlencoded"  );
        postRequest.setHeader("Accept"          ,  "*/*"                                );
        postRequest.setHeader("Connection"      ,  "keep-alive"                         );
        postRequest.setHeader("Accept-Encoding" ,  "gzip, deflate, br"                  );

        // TODO Set Params - nvp
        List<NameValuePair> prm = new ArrayList<>();
        prm.add(new BasicNameValuePair("act", "get_token"));

        postRequest.setEntity(new UrlEncodedFormEntity(prm, "UTF-8"));

        // TODO - Execute !
        HttpResponse response = client.execute(postRequest);
        ResponseHandler<String> handler = new BasicResponseHandler();
        String body = handler.handleResponse(response);

        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(body);

        // TODO - Getting Success Token
        String token = json.get("result").toString();

        /**
         *
         *
         * Login Proc Start
         */

        // TODO - Login Set Params
        Map<String, String> params = new HashMap<>();
        params.put("repage"     , "reload"  );
        params.put("page_chk"   , ""        );
        params.put("isSSL"      , "Y"       );
        params.put("browser"    , "pc"      );
        params.put("token"      , token     );
        params.put("mb_id"      , id        );
        params.put("mb_pw"      , pw        );
        params.put("url"        , "/main/module/loginClass.php"             );
        params.put("url_ssl"    , "https://ssl.filebogo.com/loginClass.php" );

        // TODO - Login Set Headeers
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type"   , "application/x-www-form-urlencoded"  );
        headers.put("User-Agent"     , "PostmanRuntime/7.26.10"             );
        headers.put("Accept"         , "*/*"                                );
        headers.put("Accept-Encoding", "gzip, deflate, br"                  );
        headers.put("Connection"     , "keep-alive"                         );

        String result = "";

        try{

            Connection.Response loginResponse = Jsoup
                        .connect("https://ssl.filebogo.com/loginClass.php")
                        .data(params)
                        .headers(headers)
                        .method(Connection.Method.POST)
                        .ignoreContentType(true)
                        .execute();

            Document doc = Jsoup.connect("https://www.filebogo.com/main/mypage.php?doc=seller_index")
                        .cookies(loginResponse.cookies()).timeout(3000000).get();

            String test = doc.select(".my_info ul li").eq(0).select(".my_info_txt").eq(2).select(".w70 .fl span").eq(0).text();

            System.out.println( test.replaceAll("[^0-9]","") );

            result = test.replaceAll("[^0-9]","");

        } catch ( Exception e ) {
            result = "ID/PW 오류 또는 통신오류";
            e.printStackTrace();
        }

        log.info("FILE BOGO RESULT ==> {}", result);
        System.out.println("FILE BOGO  RESULT ==> "+ result);

        return result;

    }


}

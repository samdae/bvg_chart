package com.braveg.mileage.adapter;

import com.braveg.mileage.adapter.ssl.SSLConfig;
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
@CrossOrigin()
@Slf4j
public class ToDisk extends SSLConfig {

    public ToDisk() {
        // TODO Constructor

    }

    public static String todisk( String id, String pw ) throws Exception {
        log.info("==========   ToDisk   ==========");

        // TODO - Encrypt Password ajax -
        HttpClient client = HttpClientBuilder.create().build();

        // TODO - Set Headers
        HttpPost postRequest = new HttpPost("https://www.todisk.com/ajax.proc.php");
        postRequest.setHeader("Content-Type"    , "application/x-www-form-urlencoded");
        postRequest.setHeader("Accept"          , "*/*"                             );
        postRequest.setHeader("Connection"      , "keep-alive"                      );
        postRequest.setHeader("Accept-Encoding" , "gzip, deflate, br"               );

        // TODO - Set Params
        List<NameValuePair> prm = new ArrayList<>();
        prm.add(new BasicNameValuePair("mode"   , "login_pw_new"  ));
        prm.add(new BasicNameValuePair("pw"     , pw                    ));

        // TODO - Params to URL Encoding...
        postRequest.setEntity( new UrlEncodedFormEntity(prm, "UTF-8") );

        // TODO - Execute !
        HttpResponse response = client.execute(postRequest);
        ResponseHandler<String> handler = new BasicResponseHandler();
        String body = handler.handleResponse(response);

        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(body);

        // TODO - Complete Password Encrypting
        String encryptPw = json.get("pw").toString();

        // TODO - Login Parameter set
        Map<String, String> params = new HashMap<>();
        params.put("act"        , "ok"      );
        params.put("mb_id"      , id        );
        params.put("mb_pw"      , encryptPw );
        params.put("log_save"   , ""        );
        params.put("repage"     , "/_main/" );
        params.put("ex_site"    , "1"       );
        params.put("bux_search_word",""     );
        params.put("mb_site"    , "todisk"  );
        params.put("g-recaptcha-response","");
        params.put("token"      , ""        );

        String result = "";

        try{

            setSSL();

            Connection.Response loginResponse = Jsoup
                    .connect("https://ssl.todisk.com/loginPrc.php")
                    .data(params)
                    .method(Connection.Method.POST)
                    .execute();

            Document doc = Jsoup.connect("https://www.todisk.com/_main/")
                    .cookies(loginResponse.cookies()).timeout(3000000)
                    .get();

            String test = doc.select("#loginBox .left_login_info tr").eq(5).select(".tt_member_info").text();

            System.out.println(test.replaceAll("[^0-9]","") );

            result = test.replaceAll("[^0-9]","");

        } catch ( Exception e ) {
            result = "ID/PW 오류 또는 통신오류";
            e.printStackTrace();
        }

        log.info("TO DISK RESULT ==> {}", result);
        System.out.println("TO DISK RESULT ==> "+ result);
        return result;

    }
}

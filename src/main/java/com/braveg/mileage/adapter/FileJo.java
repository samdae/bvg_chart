package com.braveg.mileage.adapter;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileJo {

    public static String filejo ( Map<String, Object> params ) throws Exception {
        String url = "https://www.filejo.com/main/module/loginPrc_auth_encrypt.php";

        // TODO - Set Params
        Map<String, String> param = new HashMap<>();
        params.forEach(
                (key, val) -> param.put( key , val.toString() )
        );

        Map<String, String> headers = new HashMap<>();
        headers.put("Host", "www.filejo.com");
        headers.put("Connection", "keep-alive");
        headers.put("sec-ch-ua", "\" Not A;Brand\";v=\"99\", \"Chromium\";v=\"90\", \"Google Chrome\";v=\"90\"");
        headers.put("Accept", "*/*");
        headers.put("X-Requested-With", "XMLHttpRequest");
        headers.put("sec-ch-ua-mobile", "?0" );
        headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.85 Safari/537.36" );
        headers.put("Sec-Fetch-Site", "same-origin");
        headers.put("Sec-Fetch-Mode", "cors");
        headers.put("Sec-Fetch-Dest", "empty");
        headers.put("Referer", "https://www.filejo.com/main/index.php");
        headers.put("Accept-Encoding", "gzip, deflate, br");
        headers.put("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7");

        try{
          Connection.Response loginResponse = Jsoup
                    .connect(url)
                    .data(param)
                    .headers(headers)
//                    .method(Connection.Method.POST)
                    .method(Connection.Method.GET)
                    .execute();

            System.out.println("Headers ==> "+ loginResponse.headers() );
            System.out.println("Body ==> "+ loginResponse.body() );

            Document doc = Jsoup.connect("https://filejo.com/main/index.php")
                    .cookies(loginResponse.cookies()).timeout(3000000).get();
            String test = doc.select("#loginBox").text();
            System.out.println(test);
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public static String getPublicKey() throws Exception {
        // TODO - GET Script for Secure Component
        String script = "https://www.filejo.com/main/js/login_auth.php";
        Document doc = Jsoup.connect(script).get();
        String docu = doc.toString();

        int docStart = docu.indexOf("var securePage = ");

        String[] configTxt = docu.substring(docStart, docStart+158).split(";");
        String securePage  = configTxt[0].substring(configTxt[0].indexOf("'")+1, configTxt[0].lastIndexOf("'"));
        String secureKey   = configTxt[1].substring(configTxt[1].indexOf("'")+1, configTxt[1].lastIndexOf("'"));
        String secureToken = configTxt[2].substring(configTxt[2].indexOf("'")+1, configTxt[2].lastIndexOf("'"));
        String chkNum      = configTxt[3].substring(configTxt[3].indexOf("'")+1, configTxt[3].lastIndexOf("'"));

        // TODO - SecureCompo Used to getting PublicKey
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost postRequest = new HttpPost("https://www.filejo.com/main/module/keysjo.php");

        postRequest.setHeader("Content-Type", "application/x-www-form-urlencoded");
        postRequest.setHeader("Accept", "*/*");
        postRequest.setHeader("Connection", "keep-alive");
        postRequest.setHeader("Accept-Encoding", "gzip, deflate, br");
        postRequest.setHeader("Referer", "https://www.filejo.com/");

        List<NameValuePair> prm = new ArrayList<>();
        prm.add(new BasicNameValuePair("securePage",  securePage    ));
        prm.add(new BasicNameValuePair("secureKey",   secureKey     ));
        prm.add(new BasicNameValuePair("secureToken", secureToken   ));
        prm.add(new BasicNameValuePair("chkNum",      chkNum        ));

        postRequest.setEntity(new UrlEncodedFormEntity(prm, "UTF-8"));

        // TODO - Execute !
        HttpResponse response = client.execute(postRequest);
        ResponseHandler<String> handler = new BasicResponseHandler();
        String body = handler.handleResponse(response);

        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject)
                parser.parse(
                        body.replace("(", "")
                            .replace(")", "")
                            .replace(";", ""));

        String[] res = json.get("res").toString().split("\\|");

        // TODO - Return Public Key
        return res[1];
    }

}

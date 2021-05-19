package com.braveg.mileage.adapter;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
@CrossOrigin()
@Slf4j
public class FileMaru {
    public FileMaru() {
        // TODO Constructor

    }

    public static String filemaru ( String id, String pw ) throws Exception {
        log.info("==========   FileMaru   ==========");

        /**
         *  Encrypt Password --
         */

        // TODO - Get Byte type data from String
        byte[] bytes = pw.getBytes("UTF-8");

        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encodedBytes = encoder.encode(bytes);

        // TODO - Get Encrypted Password data
        String encPw = new String(encodedBytes);

        // TODO - Set Parameter....
        Map<String, String> params = new HashMap<>();
        params.put("email"   , id   );
        params.put("key"     , encPw);
        params.put("log_save", "Y"  );
        params.put("log_safe", "Y"  );
        params.put("sns_chk" , "N"  );
        params.put("sess"    , "ecnmqjpeq1gsjpgfu39c52n5n4");
        params.put("platType", "p"  );
        params.put("dm"      , ".filemaru.com"  );

        // TODO Set Headers...
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type"  , "multipart/form-data;" );
        headers.put("User-Agent"    , "PostmanRuntime/7.26.10" );
        headers.put("Accept"        , "*/*" );
        headers.put("Accept-Encoding","gzip, deflate, br" );
        headers.put("Connection"    , "keep-alive" );

        String result = "";

        try {

            Connection.Response loginResponse = Jsoup
                    .connect("https://ssl.filemaru.com/loginPrcMaru.php")
                    .data(params)
                    .headers(headers)
                    .method(Connection.Method.POST)
                    .execute();

            log.info("FILE MARU Response Headers ==> {}", loginResponse.headers() );
            log.info("FILE MARU Response Body ==> {}", loginResponse.body() );

            Document doc = Jsoup.connect("https://www.filemaru.com/?doc=list_sub&cate=MOV")
                    .cookies(loginResponse.cookies()).timeout(3000000).get();

            String test = doc.select("#loginbox .info2 .cgb").text();

            System.out.println( test.replaceAll("[^0-9]","") );

            result = test.replaceAll("[^0-9]","");

        } catch ( IOException e ) {
            result = "ID/PW 오류 또는 통신오류";
            e.printStackTrace();
        }

        log.info("FILE MARU RESULT ==> {}", result);
        System.out.println("FILE MARU  RESULT ==> "+ result);
        return result;
    }


}

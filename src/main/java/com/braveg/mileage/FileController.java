package com.braveg.mileage;

import com.braveg.mileage.adapter.DodoFile;
import com.braveg.mileage.adapter.FileBogo;
import com.braveg.mileage.adapter.FileCity;
import com.braveg.mileage.adapter.FileMaru;
import com.braveg.mileage.adapter.FileSun;
import com.braveg.mileage.adapter.Jjinple;
import com.braveg.mileage.adapter.OnDisk;
import com.braveg.mileage.adapter.PdPop;
import com.braveg.mileage.adapter.ToDisk;
import com.braveg.mileage.adapter.YesFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/file-mileage")
@CrossOrigin()
public class FileController {

//    @PostMapping( value = "/get/PublicKey", produces = "application/json; charset=utf8")
//    @ResponseBody
//    public String getPublicKey() throws Exception {
//        String key = FileJo.getPublicKey();
//        System.out.println(key);
//        return key;
//    }

    @PostMapping(value = "/dodo",  produces = "application/json; charset=utf8")
    @ResponseBody
    public String dodo(@RequestBody Map<String, Object> params) throws Exception {
        String id = params.get("id").toString();
        String pw = params.get("pw").toString();
        return DodoFile.dodofile(id,pw);
    }
    @PostMapping( value = "/bogo",  produces = "application/json; charset=utf8")
    @ResponseBody
    public String bogo(@RequestBody Map<String, Object> params) throws Exception {
        String id = params.get("id").toString();
        String pw = params.get("pw").toString();
        return FileBogo.filebogo(id,pw);
    }
    @PostMapping(value = "/city", produces = "application/json; charset=utf8")
    @ResponseBody
    public String city(@RequestBody Map<String, Object> params) throws Exception {
        String id = params.get("id").toString();
        String pw = params.get("pw").toString();
        return FileCity.filecity(id,pw);
    }
    @PostMapping(value = "/maru",  produces = "application/json; charset=utf8")
    @ResponseBody
    public String maru(@RequestBody Map<String, Object> params) throws Exception {
        String id = params.get("id").toString();
        String pw = params.get("pw").toString();
        return FileMaru.filemaru(id,pw);
    }
    @PostMapping(value = "/sun",  produces = "application/json; charset=utf8")
    @ResponseBody
    public String sun(@RequestBody Map<String, Object> params) throws Exception {
        String id = params.get("id").toString();
        String pw = params.get("pw").toString();
        return FileSun.filesun(id,pw);
    }
    @PostMapping(value = "/jjin",  produces = "application/json; charset=utf8")
    @ResponseBody
    public String jjin(@RequestBody Map<String, Object> params) throws Exception {
        String id = params.get("id").toString();
        String pw = params.get("pw").toString();
        return Jjinple.Jjinple(id,pw);
    }
    @PostMapping(value = "/on",  produces = "application/json; charset=utf8")
    @ResponseBody
    public String on(@RequestBody Map<String, Object> params) throws Exception {
        String id = params.get("id").toString();
        String pw = params.get("pw").toString();
        return OnDisk.ondisk(id,pw);
    }
    @PostMapping(value = "/pop",  produces = "application/json; charset=utf8")
    @ResponseBody
    public String pop(@RequestBody Map<String, Object> params) throws Exception {
        String id = params.get("id").toString();
        String pw = params.get("pw").toString();
        return PdPop.pdpop( id,pw );
    }
    @PostMapping(value = "/to",  produces = "application/json; charset=utf8")
    @ResponseBody
    public String to(@RequestBody Map<String, Object> params) throws Exception {
        String id = params.get("id").toString();
        String pw = params.get("pw").toString();
        return ToDisk.todisk(id,pw);
    }
    @PostMapping(value = "/yes",  produces = "application/json; charset=utf8")
    @ResponseBody
    public String yes(@RequestBody Map<String, Object> params) throws Exception {
        String id = params.get("id").toString();
        String pw = params.get("pw").toString();
        return YesFile.yesfile(id,pw);
    }
    @PostMapping(value = "/jo",  produces = "application/json; charset=utf8")
    @ResponseBody
    public String jo(@RequestBody Map<String, Object> params) throws Exception {
//        System.out.println( params );
        //FileJo.filejo(id,pw);
        return "";
    }

//    @PostMapping(value = "/fileDownload",  produces = "application/json; charset=utf8")
//    @ResponseBody
//    public void fileDownload(@RequestBody Map<String, Object> params) throws Exception {
//
//    }



}

package com.braveg.error;


import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @Override
    public String getErrorPath() {
        return null;
    }

    @RequestMapping(value = "/error")
    public String handleError(HttpServletRequest req){
        Object status = req.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        int statusCode = Integer.parseInt( status.toString() );

        if(statusCode == HttpStatus.BAD_REQUEST.value()){
            System.out.println(" 400 에러 ");
            return "/errors/400.html";
        }
        if(statusCode == HttpStatus.NOT_FOUND.value()){
            return "/errors/404.html";
        }
        if (statusCode == HttpStatus.FORBIDDEN.value()) {
            System.out.println(" 500 에러 ");
            return "/errors/500.html";
        }

        return "/errors/error.html";
    }
}

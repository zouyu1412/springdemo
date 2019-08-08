package com.ssp.higher.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author:zouyu
 * @Date:2019/8/8 12:58
 */
@RestController
public class HelloWorldController {

    @Value("${api.appKey}")
    private String name;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("test")
    public String tset(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        logger.info("haha");
//        try {
//            httpServletResponse.getWriter().write("hahinn");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return "hello world";
    }

}

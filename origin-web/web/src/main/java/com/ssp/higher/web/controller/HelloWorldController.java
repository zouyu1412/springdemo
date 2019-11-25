package com.ssp.higher.web.controller;

import com.ssp.higher.base.pojos.Result;
import com.ssp.higher.business.myservice.FeignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author:zouyu
 * @Date:2019/8/8 12:58
 */
@RestController
public class HelloWorldController {

    @Value("${api.appKey}")
    private String name;

    @Autowired
    private FeignService feignService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("test")
    public String tset(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        logger.info("haha");

        Result test = feignService.test(11, 1001);
        System.out.println(true);

        return "hello world";
    }

}

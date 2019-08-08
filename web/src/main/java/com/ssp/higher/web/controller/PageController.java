package com.ssp.higher.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author:zouyu
 * @Date:2019/8/8 15:55
 */
@Controller
public class PageController {

    @RequestMapping("index")
    public String test(){
        return "index";
    }

}

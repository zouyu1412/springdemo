package com.ssp.higher.web.controller;

import com.ssp.higher.web.annotation.RequestPage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author:zouyu
 * @Date:2019/8/8 15:55
 */
@Controller
@RequestPage(name = "index")
public class PageController {

    @RequestMapping("index")
    public String test(){
        return "index";
    }

}

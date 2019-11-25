package com.ssp.higher.web.controller;

import com.ssp.higher.base.pojos.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * @Author:zouyu
 * @Date:2019/8/22 10:36
 */
@Controller
public class WelcomeController {

    @RequestMapping("welcome")
    public String welcome(Model model, HttpServletRequest httpServletRequest){

        User user = new User();
        user.setId(1);
        user.setName("liming");
        user.setCityTravels(new ArrayList<String>(){{add("changsha");add("beijing");add("shanghai");}});

        httpServletRequest.setAttribute("user",user);
        model.addAttribute("aaa","啊啊啊");

        return "welcome";
    }
}

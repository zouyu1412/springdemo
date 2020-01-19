package com.ssp.hello;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author:zouyu
 * @Date:2019/11/25 17:00
 */
@RestController
public class SayHelloController {

    @RequestMapping("hello/{name}")
    public String sayHello(@PathVariable("name")String name){
        return name+"is saying hello";
    }

}

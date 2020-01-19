package com.ssp.caller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @Author:zouyu
 * @Date:2019/11/25 17:08
 */
@RestController
@Configuration
public class AskController {

    @Value("${spring.application.name}")
    private String name;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    InstanceService instanceService;

    @RequestMapping("ask")
    public String ask(){
        //从服务提供者hello-server中请求sayHello服务

        String askHello = restTemplate.getForEntity("http://HELLO-SERVER/hello/{name}",String.class,name).getBody();

        return askHello;
    }

    @RequestMapping("test")
    public String test(){
        Instance sd = instanceService.getInstanceByServiceId("sd");
        return sd.toString();
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}

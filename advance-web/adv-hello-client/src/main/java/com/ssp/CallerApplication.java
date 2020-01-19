package com.ssp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @Author:zouyu
 * @Date:2019/11/25 17:03
 */
@SpringBootApplication
@EnableFeignClients
@EnableCircuitBreaker
public class CallerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CallerApplication.class,args);
    }

}

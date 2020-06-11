package com.ssp.higher;

import com.ssp.higher.base.pojos.Body;
import com.ssp.higher.base.pojos.User;
import com.ssp.starter.filter.EnableLogFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.LinkedHashMap;

@SpringBootApplication
@EnableFeignClients
@EnableLogFilter
public class WebApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(WebApplication.class, args);
		Object getUser = context.getBean("user");

		String[] beanNamesForType = context.getBeanNamesForType(Body.class);
		for(String name:beanNamesForType){
			System.out.println(name+"=================");

		}

	}

}

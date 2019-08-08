package com.ssp.higher.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //这里可以添加多个拦截器
//        registry.addInterceptor(new TimeInterceptor()).addPathPatterns("/**");
//        registry.addInterceptor(new AllowOriginInterceptor()).addPathPatterns("/**").excludePathPatterns("/static/**", "/favicon.ico");
    }


}

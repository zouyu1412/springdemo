package com.ssp.starter.filter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Content 注册到上下文
 * @Author:zouyu
 * @Date:2019/11/25 11:12
 */
@Configuration
@ConditionalOnClass({LogFilterRegistrationBean.class,LogFilter.class})  //只有当这两个class在类路径上 配置才生效
public class LogFilterAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(LogFilterRegistrationBean.class)
    public LogFilterRegistrationBean logFilterRegistrationBean(){
        return new LogFilterRegistrationBean();
    }

}

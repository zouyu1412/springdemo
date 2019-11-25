package com.ssp.starter.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;

/**
 * @Content 定义LogFilterRegistrationBean将LogFilter封装成SpringBean
 * @Author:zouyu
 * @Date:2019/11/25 11:02
 */
public class LogFilterRegistrationBean extends FilterRegistrationBean<LogFilter> {

    public LogFilterRegistrationBean(){
        super();
        this.setFilter(new LogFilter());//添加过滤器
        this.addUrlPatterns("/*");//匹配所以路径
        this.setName("LogFilter");//设置过滤器名
        this.setOrder(1);//设置优先级
    }
}

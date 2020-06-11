package com.ssp.higher.web.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.SortedSet;

/**
 * @Author:zouyu
 * @Date:2020/3/9 12:12
 */
@Component
public class AwareTestConfig implements ApplicationContextAware, BeanNameAware, EnvironmentAware {

    @Override
    public void setBeanName(String name) {
        System.out.println("set bean name:"+name);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("set setApplicationContext:");
    }

    @Override
    public void setEnvironment(Environment environment) {
        System.out.println("set environment:");
    }
}

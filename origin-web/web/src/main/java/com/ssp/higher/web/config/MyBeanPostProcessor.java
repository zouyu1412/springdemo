package com.ssp.higher.web.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * @Author:zouyu
 * @Date:2020/3/4 9:56
 */
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {

    Logger logger = LoggerFactory.getLogger(MyBeanPostProcessor.class);

    //Spring BeanPostProcessor的使用：
    // bean赋值
    // @Autowire
    // ApplicationContextAware接口赋值
    // @Async
    //生命周期注解
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        logger.info("postProcessBeforeInitialization..."+beanName);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        logger.info("postProcessAfterInitialization..."+beanName);
        return bean;
    }

}

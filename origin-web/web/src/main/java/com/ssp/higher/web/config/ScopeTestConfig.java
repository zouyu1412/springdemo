package com.ssp.higher.web.config;

import com.ssp.higher.base.pojos.User;
import org.springframework.context.annotation.*;

/**
 * @Author:zouyu
 * @Date:2020/3/3 10:37
 */
@Configuration
@Import(MyImportSelector.class)
public class ScopeTestConfig {

    /**
     * Scope注解四个取值：
     * 默认值 单例 容器启动创建对象
     * prototype 多例  懒加载
     * request 一次请求创建一个实例
     * session 一个session创建一个实例
     */
//    @Lazy 懒加载
//    @Scope("prototype")
    @Bean(value = "user", initMethod = "myInit", destroyMethod = "myDestroy")
    public User getUser(){
        return new User(1,"zy");
    }

//    @Conditional(WindowsCondition.class)
//    @Bean("bill")
//    public User getUser1(){
//        System.out.printf("getUser1 =========================");
//        return new User(1,"zy1");
//    }
//
//    @Conditional(LinuxCondition.class)
//    @Bean("linus")
//    public User getUser2(){
//        System.out.printf("getUser2 =========================");
//        return new User(1,"zy2");
//    }

}

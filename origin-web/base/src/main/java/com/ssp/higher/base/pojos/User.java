package com.ssp.higher.base.pojos;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;

import java.util.List;

/**
 * @Author:zouyu
 * @Date:2019/8/22 10:39
 */
public class User implements InitializingBean, DisposableBean, BeanNameAware, BeanFactoryAware, BeanClassLoaderAware {

    private int id;
    private String name;
    private List<String> cityTravels;

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void myInit(){
        System.out.println("myInit");
    }

    public void myDestroy(){
        System.out.println("myDestroy");
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getCityTravels() {
        return cityTravels;
    }

    public void setCityTravels(List<String> cityTravels) {
        this.cityTravels = cityTravels;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cityTravels=" + cityTravels +
                '}';
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        System.out.println("BeanClassLoaderAware --> setBeanClassLoader");
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("BeanFactoryAware --> setBeanFactory");
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("BeanNameAware --> setBeanName");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("DisposableBean --> destroy");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean --> afterPropertiesSet");
    }
}

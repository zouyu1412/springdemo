package com.ssp.starter.filter;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Content:
 *  因为Starter是通过jar包的方式引入项目中的,对应的classes并不在项目Spring的扫描范围内，无法自动引入项目的Spring管理中
 *  对此需要用额外的方式将LogFilterAutoConfiguration引入到项目的Spring管理中，可以通过注解的方式将配置类引入到Spring的扫描范围
 * @Author:zouyu
 * @Date:2019/11/25 11:19
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(LogFilterAutoConfiguration.class) //引入LogFilterAutoConfiguration配置类
public @interface EnableLogFilter {
}

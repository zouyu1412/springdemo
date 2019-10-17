package com.ssp.higher.web.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestPage {
    /**
     * Controller所表示的页面名称
     * @return
     */
    public String name();
    
    /**
     * Controller所表示的页面说明
     * @return
     */
    public String description() default "";
}

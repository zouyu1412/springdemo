package com.ssp.higher.web.config;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @Author:zouyu
 * @Date:2020/3/3 11:50
 */
public class MyImportSelector implements ImportSelector {

    //自定义需要导入的组件
    //AnnotationMetadata:当前标注@Import注解的类的所有注解信息
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {

        return new String[]{"com.ssp.higher.base.pojos.Mouth","com.ssp.higher.base.pojos.Nose"};
    }
}

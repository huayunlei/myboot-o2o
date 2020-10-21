package com.ihomefnt.o2o.intf.manager.util.common.bean;

import java.lang.annotation.*;


/**
 * Created by shirely_geng on 2015年1月25日
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Json {

    /**
     * 针对List 泛型对象化映射,单元类型定义
     */
    Class[] types() default java.lang.Object.class;

    /**
     * bean key path定义
     */
    String path() default "";

}
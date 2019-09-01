package com.study.mvc.annotation;

import java.lang.annotation.*;

/**
 * 此注解用来将用该注解的Bean 变量 ，变量里面的实例注入进去
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Qualifier {
    String value() default "";
}
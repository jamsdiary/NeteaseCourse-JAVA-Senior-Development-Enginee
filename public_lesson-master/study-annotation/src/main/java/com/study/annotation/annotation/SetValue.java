package com.study.annotation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SetValue {

	/**
	 * 需要的bean的类
	 * 
	 * @return
	 */
	Class<?> beanClass();

	/**
	 * bean的哪个方法
	 */
	String method();

	/**
	 * 传人的参数字段
	 */
	String paramField();

	/**
	 * 要获取取得的对象上的哪个属性
	 */
	String targetField() default "";
	
}

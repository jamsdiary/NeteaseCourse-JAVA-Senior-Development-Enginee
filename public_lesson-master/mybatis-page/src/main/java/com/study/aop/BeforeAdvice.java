package com.study.aop;

import java.lang.reflect.Method;

import org.springframework.aop.MethodBeforeAdvice;

/**
 * 前置通知
 */
public class BeforeAdvice implements MethodBeforeAdvice {

    /**
     * method 方法信息
     * args 参数
     * target 被代理的目标对象
     */
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("-----------------前置通知-----------------");
    }
}

package com.study.security.auth.client.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: IgnoreClientToken
 * @Description: 忽略服务鉴权
 * @Author: 网易云课堂微专业-java高级开发工程师
 * @Date: 2019/6/11 15:39
 * @Version: 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD,ElementType.TYPE})
public @interface IgnoreClientToken {
}

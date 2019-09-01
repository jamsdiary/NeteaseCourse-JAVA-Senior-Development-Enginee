package com.study.order.config;

import com.study.security.common.handler.GlobalExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by zhongxin on 2019/5/31.
 */
@Configuration("admimWebConfig")
@Primary
public class WebConfiguration implements WebMvcConfigurer {
    @Bean
    GlobalExceptionHandler getGlobalExceptionHandler() {
        return new GlobalExceptionHandler();
    }
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        // 配置服务认证拦截器器
//        registry.addInterceptor(getServiceAuthRestInterceptor()). addPathPatterns(getIncludePathPatterns());
//        // 配置⽤用户认证拦截器器
//        registry.addInterceptor(getUserAuthRestInterceptor()). addPathPatterns(getIncludePathPatterns());
//    }
//
//    @Bean
//    ServiceAuthRestInterceptor getServiceAuthRestInterceptor() {
//        return new ServiceAuthRestInterceptor();
//    }
//
//    @Bean
//    UserAuthRestInterceptor getUserAuthRestInterceptor() {
//        return new UserAuthRestInterceptor();
//    }
//    /**     * 需要⽤用户和服务认证判断的路路径     * @return     */
//    private ArrayList<String> getIncludePathPatterns() {
//        ArrayList<String> list = new ArrayList<>();
//        String[] urls = {"/cloud/classroom/user_study/current/user/list",
//                "/cloud/classroom/user_study","/cloud/classroom/course"};
//        Collections.addAll(list, urls);
//        return list;
//    }
}

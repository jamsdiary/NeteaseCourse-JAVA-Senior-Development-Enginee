package com.study.security.auth.client.configuration;

import com.study.security.auth.client.config.CloudAuthClientProperties;
import com.study.security.auth.client.config.ServiceAuthConfig;
import com.study.security.auth.client.config.UserAuthConfig;
import com.study.security.auth.client.interceptor.ServiceAuthRestInterceptor;
import com.study.security.auth.client.interceptor.UserAuthRestInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName AutoConfiguration
 * @Description Feign使用okhttp配置
 * @Author 网易云课堂微专业-java高级开发工程师
 * @Date 2019/6/11 15:39
 * @Version 1.0
 */
@Configuration
@ComponentScan({"com.study.security.auth.client"})
//@EnableFeignClients(basePackages = "com.study.security.auth.client.feign")
//@EnableConfigurationProperties(value = {CloudAuthClientProperties.class}) // 启用配置
public class AutoConfiguration {
    @Bean
    ServiceAuthConfig getServiceAuthConfig() {
        return new ServiceAuthConfig();
    }

    @Bean
    UserAuthConfig getUserAuthConfig() {
        return new UserAuthConfig();
    }

//    @ConditionalOnClass({org.springframework.web.servlet.handler.HandlerInterceptorAdapter.class,
//            org.springframework.web.servlet.config.annotation.WebMvcConfigurer.class})
//    static class AuthClientInterceptorConfiguration {
//        @Bean
//        ServiceAuthRestInterceptor serviceAuthRestInterceptor() {
//            return new ServiceAuthRestInterceptor();
//        }
//
//        @Bean
//        UserAuthRestInterceptor userAuthRestInterceptor() {
//            return new UserAuthRestInterceptor();
//        }
//
//        // 增加权限验证的拦截器
//        @Bean
//        public org.springframework.web.servlet.config.annotation.WebMvcConfigurer authInterceptorWebMvcConfigurer(ServiceAuthRestInterceptor serviceAuthRestInterceptor,
//                                                                                                                  UserAuthRestInterceptor userAuthRestInterceptor,
//                                                                                                                  CloudAuthClientProperties cloudAuthClientProperties) {
//            return new org.springframework.web.servlet.config.annotation.WebMvcConfigurer() {
//                @Override
//                public void addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry registry) {
//                    /* 服务校验 */
//                    registry.addInterceptor(serviceAuthRestInterceptor).
//                            addPathPatterns(cloudAuthClientProperties.getIncludePathPatterns());
//                    /* 用户校验 */
//                    registry.addInterceptor(userAuthRestInterceptor).
//                            addPathPatterns(cloudAuthClientProperties.getIncludePathPatterns());
//                }
//            };
//        }
//    }


}

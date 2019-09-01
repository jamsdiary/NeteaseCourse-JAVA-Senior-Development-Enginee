package com.study.order.config;

/**
 * Created by zhongxin on 2019/5/14.
 */

import feign.RequestInterceptor;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * Feign配置注册（全局）
 *
 * @author simon
 * @create 2018-08-20 11:44
 **/
//@Configuration
public class FeignSupportConfig extends FeignClientsConfiguration {
    /**
     * feign请求拦截器
     *
     * @return
     */
    @Bean
    public RequestInterceptor requestInterceptor(){
        return new FeignBasicAuthRequestInterceptor();
    }
}

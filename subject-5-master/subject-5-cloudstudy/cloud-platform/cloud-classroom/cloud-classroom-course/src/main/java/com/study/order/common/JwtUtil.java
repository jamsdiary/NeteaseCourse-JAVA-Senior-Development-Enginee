package com.study.order.common;

import com.study.security.common.context.BaseContextHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class JwtUtil {

    public static String getUserId() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        return BaseContextHandler.getUserID();
        //BaseContextHolder.getUserId();
        //return request.getHeader("userId");
    }

    public static String getRoleNames(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        return request.getHeader("authorities");
    }

}

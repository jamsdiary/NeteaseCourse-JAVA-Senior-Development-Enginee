package com.study.springboot.demo1;

import org.springframework.beans.factory.annotation.Value;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class FilterDemo1 implements Filter {
    // 冒号后面，就是一个默认值
    @Value("${name:default-tony}")
    public String name;

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("自定义filter被执行:" + name);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}

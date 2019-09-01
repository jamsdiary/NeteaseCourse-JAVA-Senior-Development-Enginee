package com.study.order.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by andy on 2018/1/2.
 */
@Component
public class CustomExceptionResolver implements HandlerExceptionResolver {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse response, Object o, Exception e) {
        ModelAndView mv = new ModelAndView(new MappingJackson2JsonView());
        Map<String, Object> map = new HashMap<>();
        map.put("status", 500);
        map.put("msg", "cloud-classroom-resource,操作失败!");
        map.put("error",e.toString());
        mv.addAllObjects(map);
        e.printStackTrace();
        logger.error(e.toString(),e);
        return mv;
    }
}
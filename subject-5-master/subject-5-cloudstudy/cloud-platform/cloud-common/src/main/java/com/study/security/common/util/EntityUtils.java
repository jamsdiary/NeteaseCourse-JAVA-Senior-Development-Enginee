package com.study.security.common.util;

import com.study.security.common.context.BaseContextHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.util.Date;

/**
 * @ClassName: EntityUtils
 * @Description: 实体类相关工具类
 * 解决问题： 1、快速对实体的常驻字段，如：crtUser、crtHost、updUser等值快速注入
 * @Author: 网易云课堂微专业-java高级开发工程师
 * @Date: 2019/6/11 15:39
 * @Version: 1.0
 */
public class EntityUtils {
    /**
     * @return void
     * @Description 快速将bean的crtUser、crtHost、crtTime、updUser、updHost、updTime附上相关值
     * @Author 网易云课堂微专业 - java高级开发工程
     * @Date 2019/7/3 16:12
     * @Param [entity] 实体bean
     **/
    public static <T> void setCreatAndUpdatInfo(T entity) {
        setCreateInfo(entity);
        setUpdatedInfo(entity);
    }

    /**
     * @return void
     * @Description 快速将bean的crtUser、crtHost、crtTime附上相关值
     * @Author 网易云课堂微专业 - java高级开发工程
     * @Date 2019/7/3 16:13
     * @Param [entity] 实体bean
     **/
    public static <T> void setCreateInfo(T entity) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String hostIp = "";
        String name = "";
        String id = "";
        if (request != null) {
            hostIp = StringUtils.defaultIfBlank(request.getHeader("userHost"), ClientUtil.getClientIp(request));
            name = StringUtils.trimToEmpty(request.getHeader("userName"));
            try {
                name = URLDecoder.decode(name, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            id = StringUtils.trimToEmpty(request.getHeader("userId"));
        }

        if (StringUtils.isBlank(name)) {
            name = BaseContextHandler.getUsername();
        }
        if (StringUtils.isBlank(id)) {
            id = BaseContextHandler.getUserID();
        }

        // 默认属性
        String[] fields = {"crtName", "crtUser", "crtHost", "crtTime"};
        Field field = ReflectionUtils.getAccessibleField(entity, "crtTime");
        // 默认值
        Object[] value = null;
        if (field != null && field.getType().equals(Date.class)) {
            value = new Object[]{name, id, hostIp, new Date()};
        }
        // 填充默认属性值
        setDefaultValues(entity, fields, value);
    }

    /**
     * @return void
     * @Description 快速将bean的updUser、updHost、updTime附上相关值
     * @Author 网易云课堂微专业 - java高级开发工程
     * @Date 2019/7/3 16:13
     * @Param [entity] 实体bean
     **/
    public static <T> void setUpdatedInfo(T entity) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String hostIp = "";
        String name = "";
        String id = "";
        if (request != null) {
            hostIp = StringUtils.defaultIfBlank(request.getHeader("userHost"), ClientUtil.getClientIp(request));
            name = StringUtils.trimToEmpty(request.getHeader("userName"));
            try {
                name = URLDecoder.decode(name, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            id = StringUtils.trimToEmpty(request.getHeader("userId"));
        }

        if (StringUtils.isBlank(name)) {
            name = BaseContextHandler.getUsername();
        }
        if (StringUtils.isBlank(id)) {
            id = BaseContextHandler.getUserID();
        }

        // 默认属性
        String[] fields = {"updName", "updUser", "updHost", "updTime"};
        Field field = ReflectionUtils.getAccessibleField(entity, "updTime");
        Object[] value = null;
        if (field != null && field.getType().equals(Date.class)) {
            value = new Object[]{name, id, hostIp, new Date()};
        }
        // 填充默认属性值
        setDefaultValues(entity, fields, value);
    }

    /***
     * @Description 依据对象的属性数组和值数组对对象的属性进行赋值
     * @Author 网易云课堂微专业 - java高级开发工程
     * @Date 2019/7/3 16:14
     * @Param [entity, fields, value] entity 对象,fields 属性数组,value 值数组
     * @return void
     **/
    private static <T> void setDefaultValues(T entity, String[] fields, Object[] value) {
        for (int i = 0; i < fields.length; i++) {
            String field = fields[i];
            if (ReflectionUtils.hasField(entity, field)) {
                ReflectionUtils.invokeSetter(entity, field, value[i]);
            }
        }
    }

    /***
     * @Description 根据主键属性，判断主键是否值为空
     * @Author 网易云课堂微专业 - java高级开发工程
     * @Date 2019/7/3 16:14
     * @Param [entity, fields, value]
     * @return void
     **/
    public static <T> boolean isPKNotNull(T entity, String field) {
        if (!ReflectionUtils.hasField(entity, field)) {
            return false;
        }
        Object value = ReflectionUtils.getFieldValue(entity, field);
        return value != null && !"".equals(value);
    }
}

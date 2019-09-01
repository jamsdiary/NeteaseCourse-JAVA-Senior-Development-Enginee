package com.study.security.common.util;

/**
 * @ClassName: StringHelper
 * @Description: 字符串操作工具类
 * @Author: 网易云课堂微专业-java高级开发工程师
 * @Date: 2019/6/11 15:39
 * @Version: 1.0
 */
public class StringHelper {
    public static String getObjectValue(Object obj){
        return obj==null?"":obj.toString();
    }
}

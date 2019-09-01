package com.study.thread.future.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;

/**
 * 串行调用http接口
 */
@Service
public class UserService {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 查询多个系统的数据，合并返回
     */
    public Object getUserInfo(String userId) {
        // 其他例子, 查数据库的多个表数据,分多次查询

        // 1. 先从调用获取用户基础信息的http接口
        long userinfoTime = System.currentTimeMillis();
        String value = restTemplate.getForObject("http://www.tony.com/userinfo-api/get?userId=" + userId, String.class);
        JSONObject userInfo = JSONObject.parseObject(value);
        System.out.println("userinfo-api用户基本信息接口调用时间为" + (System.currentTimeMillis() - userinfoTime));

        // 2. 再调用获取用户积分信息的接口
        long integralApiTime = System.currentTimeMillis();
        String intergral = restTemplate.getForObject("http://www.tony.com/integral-api/get?userId=" + userId,
                String.class);
        JSONObject intergralInfo = JSONObject.parseObject(intergral);
        System.out.println("integral-api积分接口调用时间为" + (System.currentTimeMillis() - integralApiTime));

        // 3、 在调用一个接口 +n秒

        // 3. 合并为一个json对象
        JSONObject result = new JSONObject();
        result.putAll(userInfo);
        result.putAll(intergralInfo);

        return result;
    }

}

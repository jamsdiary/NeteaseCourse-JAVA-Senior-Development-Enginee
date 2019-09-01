package com.study.thread.future.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 调用http接口
 */
@Service
public class UserServiceCountLatch {
    ExecutorService executorService = Executors.newCachedThreadPool();

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 查询多个系统的数据，合并返回
     */
    public Object getUserInfo(String userId) throws InterruptedException {
        CountDownLatch count = new CountDownLatch(2);
        ArrayList<JSONObject> values = new ArrayList<>();
        // 你可以封装成一个 提交URL 就能自动多线程调用的 工具
            executorService.submit(() -> {
                // 1. 先从调用获取用户基础信息的http接口
                long userinfoTime = System.currentTimeMillis();
                String value = restTemplate.getForObject("http://www.tony.com/userinfo-api/get?userId=" + userId, String.class);
                JSONObject userInfo = JSONObject.parseObject(value);
                System.out.println("userinfo-api用户基本信息接口调用时间为" + (System.currentTimeMillis() - userinfoTime));
                values.add(userInfo);
                count.countDown();
            });
            executorService.submit(() -> {
                // 2. 再调用获取用户积分信息的接口
                long integralApiTime = System.currentTimeMillis();
                String intergral = restTemplate.getForObject("http://www.tony.com/integral-api/get?userId=" + userId,
                        String.class);
                JSONObject intergralInfo = JSONObject.parseObject(intergral);
                System.out.println("integral-api积分接口调用时间为" + (System.currentTimeMillis() - integralApiTime));
                values.add(intergralInfo);
                count.countDown();
        });

        count.await();// 等待计数器归零

        // 3. 合并为一个json对象
        JSONObject result = new JSONObject();
        for (JSONObject value : values) {
            result.putAll(value);
        }
        return result;
    }
}
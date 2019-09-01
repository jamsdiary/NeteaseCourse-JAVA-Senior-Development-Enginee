package com.study.thread.future.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.*;

/**
 * 串行调用http接口
 */
@Service
public class UserServiceFutureTask {
    ExecutorService executorService = Executors.newCachedThreadPool();
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 查询多个系统的数据，合并返回
     */
    public Object getUserInfo(String userId) throws ExecutionException, InterruptedException {
        // 其他例子, 查数据库的多个表数据,分多次查询

        // 原味爱好
        // Future < >  Callable
        // 1 和runnable一样的业务定义.  但是本质上是有区别的:  返回值 异常 call run.
        Callable<JSONObject> callable = new Callable<JSONObject>() {
            @Override
            public JSONObject call() throws Exception {
                // 1. 先从调用获取用户基础信息的http接口
                long userinfoTime = System.currentTimeMillis();
                String value = restTemplate.getForObject("http://www.tony.com/userinfo-api/get?userId=" + userId, String.class);
                JSONObject userInfo = JSONObject.parseObject(value);
                System.out.println("userinfo-api用户基本信息接口调用时间为" + (System.currentTimeMillis() - userinfoTime));
                return userInfo;
            }
        };

        // 通过多线程运行callable
        NeteaseFutureTask<JSONObject> userInfoFutureTask = new NeteaseFutureTask<>(callable);
        new Thread(userInfoFutureTask).start();

        NeteaseFutureTask<JSONObject> intergralInfoTask = new NeteaseFutureTask(() -> {
            // 2. 再调用获取用户积分信息的接口
            long integralApiTime = System.currentTimeMillis();
            String intergral = restTemplate.getForObject("http://www.tony.com/integral-api/get?userId=" + userId,
                    String.class);
            JSONObject intergralInfo = JSONObject.parseObject(intergral);
            System.out.println("integral-api积分接口调用时间为" + (System.currentTimeMillis() - integralApiTime));
            return intergralInfo;
        });
        new Thread(intergralInfoTask).start();

        // 3. 合并为一个json对象
        JSONObject result = new JSONObject();
        result.putAll(userInfoFutureTask.get()); // 会等待任务执行结束
        result.putAll(intergralInfoTask.get());

        return result;
    }

}

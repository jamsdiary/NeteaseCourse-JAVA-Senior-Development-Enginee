package com.study.controller;

import com.study.locks.RedisDistributionLock;
import com.study.locks.RedisLockImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 分布式锁验证
 *
 * @Auther: allen
 * @Date: 2018/12/2 18:00
 */
@RestController
@RequestMapping("/redis")
public class RedisLockController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RedisDistributionLock redisLockImpl;

    private static final String LOCK_NO = "redis_lock_no_";

    // 100张票
    private int tickets = 100;

    /**
     * 模拟3个线程同时执行业务，修改资源
     */
    @GetMapping("lock")
    public void testRedisLock(){
        // 三个线程模拟三个窗口售票
        for (int i=1; i<=3; i++) {
            String name = "窗口" + i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (tickets > 0) {
                        //创建一个redis分布式锁
                        RedisLockImpl redisLock = new RedisLockImpl(redisTemplate);
                        //加锁时间
                        Long lockTime = redisLock.lock((LOCK_NO + 1) + "", name);
                        try {
                            if (tickets > 0) {
                                if (lockTime != null) {
                                    //开始执行任务
                                    System.out.println(name + "正在出售第 " + tickets-- + " 张票");
                                }
                            }
                        } finally {
                            //任务执行完毕 关闭锁
                            redisLock.unlock((LOCK_NO + 1) + "", lockTime, name);
                        }
                    }
                }
            }).start();
        }

    }


}

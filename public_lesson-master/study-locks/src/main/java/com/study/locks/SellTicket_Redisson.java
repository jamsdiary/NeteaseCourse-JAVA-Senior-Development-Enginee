package com.study.locks;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.config.Config;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 模拟多线程下资源竞争 - Redisson解决
 * @Auther: allen
 * @Date: 2018/12/2 11:05
 */
public class SellTicket_Redisson implements Runnable {
    // 100张票
    private int tickets = 100;

    // Redisson 实现
    RLock lock = getRedissonLock();

    private RLock getRedissonLock() {
        Config config = new Config();
        // use "rediss://" for SSL connection
        // config.useClusterServers().addNodeAddress("redis://192.168.100.100:6379");
        config.useSingleServer().setAddress("redis://localhost:6379");
        Redisson redisson = (Redisson) Redisson.create(config);
        // 获得锁对象实例
        RLock lock = redisson.getLock("foobar");
        return lock;
    }

    @Override
    public void run() {
        while (tickets >0) {

            // 方案二
            lock.lock(); // 获取锁
            try {
                if (tickets > 0) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "正在出售第 " + tickets-- + " 张票");
                }
            } finally {
                lock.unlock(); // 释放锁
            }
        }
    }
}

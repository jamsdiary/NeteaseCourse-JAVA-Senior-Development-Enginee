package com.study.locks;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.config.Config;

/**
 * 模拟多线程下资源竞争 - Redisson解决
 * @Auther: allen
 * @Date: 2018/12/2 11:05
 */
public class SellTicket_Redis implements Runnable {
    // 100张票
    private int tickets = 100;

//    RedisDistributeLock lock = new RedisDistributeLock();

    @Override
    public void run() {
        while (tickets >0) {

                // 方案二
                // lock.lock(); // 获取锁
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
                    // lock.unlock(); // 释放锁
                }
        }
    }
}

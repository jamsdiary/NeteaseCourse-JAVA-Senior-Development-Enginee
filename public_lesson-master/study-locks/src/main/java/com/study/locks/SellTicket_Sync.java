package com.study.locks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 模拟多线程下资源竞争 - synchronized解决
 *
 * @Auther: allen
 * @Date: 2018/12/2 11:05
 */
public class SellTicket_Sync implements Runnable {
    // 100张票
    private int tickets = 100;

    Lock lock = new ReentrantLock();

    @Override
    public void run() {
        while (tickets > 0) {
            lock.lock(); // 获取锁
            //synchronized (this) {
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
                lock.unlock();
            }
        }
    }
}

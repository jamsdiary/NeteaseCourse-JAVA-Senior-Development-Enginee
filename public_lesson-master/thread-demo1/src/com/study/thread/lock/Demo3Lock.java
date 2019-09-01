package com.study.thread.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Demo3Lock {
    int i = 0;

    public static void main(String[] args) throws InterruptedException {
        Demo3Lock demo3 = new Demo3Lock();
        Lock lock = new NeteaseLock();

        Thread[] threads = new Thread[7];
        for (int i = 0; i < threads.length; i++) {
            int finalI = i;
            Thread thread = new Thread(() -> {
                System.out.println("线程-" + finalI + " 开始执行");

                for (int j = 0; j < 10000; j++) {
                    lock.lock(); // 获取锁， 没拿到就阻塞等待
                    try {
                        demo3.i++;// load i  ; ++;  save i
                    } finally {
                        lock.unlock();// 释放锁
                    }
                }
                System.out.println("线程-" + finalI + " 执行结束");

            });
            threads[i] = thread;
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }
        System.out.println("执行结束：" + demo3.i);
    }
}

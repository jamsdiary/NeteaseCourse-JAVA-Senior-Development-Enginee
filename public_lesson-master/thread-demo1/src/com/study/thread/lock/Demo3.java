package com.study.thread.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Demo3 {
    int i = 0;

    public static void main(String[] args) throws InterruptedException {
        Demo3 demo3 = new Demo3();

        Thread[] threads = new Thread[7];
        for (int i = 0; i < threads.length; i++) {
            int finalI = i;
            Thread thread = new Thread(() -> {
                System.out.println("线程-" + finalI + " 开始执行");

                for (int j = 0; j < 10000; j++) {
                    demo3.i++;
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

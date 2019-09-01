package com.study.lock.lock;

import java.util.concurrent.atomic.AtomicInteger;

// 两个线程，对 i 变量进行递增操作
public class LockDemo {
    // volatile int i = 0;
    AtomicInteger i = new AtomicInteger(0);


    public void add() {
        // TODO xx00
        // i++;// 三个步骤
        i.incrementAndGet();
    }

    public static void main(String[] args) throws InterruptedException {
        LockDemo ld = new LockDemo();

        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    ld.add();
                }
            }).start();
        }
        Thread.sleep(2000L);
        System.out.println(ld.i);
    }
}

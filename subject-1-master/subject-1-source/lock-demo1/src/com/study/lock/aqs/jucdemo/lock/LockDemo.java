package com.study.lock.aqs.jucdemo.lock;

import java.util.concurrent.atomic.AtomicInteger;

public class LockDemo {
    volatile int i = 0;

    public void add() {
        // TODO xx00
        i++;// 三个步骤
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

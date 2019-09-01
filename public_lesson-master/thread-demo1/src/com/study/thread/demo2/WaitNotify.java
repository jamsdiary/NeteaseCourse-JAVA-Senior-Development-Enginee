package com.study.thread.demo2;

import org.junit.Test;

import java.util.concurrent.locks.LockSupport;

public class WaitNotify {
    /**
     * 包子店
     */
    public static Object baozidian = null;

    @Test
    public void waitNotifyTest() throws Exception {
        // 启动线程
        new Thread(() -> {
            if (baozidian == null) { // 如果没包子，则进入等待
                try {
                    Thread.sleep(5000L);
                    synchronized (this) {
                        System.out.println("1、进入等待");
                        this.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("2、买到包子，回家");
        }).start();
        // 3秒之后，生产一个包子
        Thread.sleep(3000L);
        baozidian = new Object();
        synchronized (this) {
            this.notifyAll(); // 一定要在wait后面调用
            System.out.println("3、通知消费者");
        }

        Thread.sleep(10000L);
    }

    @Test
    public void parkunparkTest() throws Exception {
        // 启动线程
        Thread thread1 = new Thread(() -> {
            if (baozidian == null) { // 如果没包子，则进入等待
                try {
                    Thread.sleep(5000L);
                    System.out.println("1、进入等待");
                    LockSupport.park();// 将当前线程挂起，等待继续运行的许可
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("2、买到包子，回家");
        });
        thread1.start();
        // 3秒之后，生产一个包子
        Thread.sleep(3000L);
        baozidian = new Object();
        System.out.println("3、通知消费者");
        LockSupport.unpark(thread1); // 给指定的线程，颁发继续运行的许可 （好比一个信号）

        Thread.sleep(10000L);
    }
}

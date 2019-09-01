package com.study.lock.readWriteLock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

// 不用读写锁
public class ReentrantReadWriteLockDemo1 {
    public static void main(String[] args)  {
        final ReentrantReadWriteLockDemo1 readWriteLockDemo1 = new ReentrantReadWriteLockDemo1();
        // 多线程同时读/写
        new Thread(() -> {
            readWriteLockDemo1.read(Thread.currentThread());
        }).start();

        new Thread(() -> {
            readWriteLockDemo1.write(Thread.currentThread());
        }).start();

        new Thread(() -> {
            readWriteLockDemo1.read(Thread.currentThread());
        }).start();
    }

    // 不管读写，只有一个线程能用， 独享锁
    public synchronized void read(Thread thread) { // 2秒
        long start = System.currentTimeMillis();
        while(System.currentTimeMillis() - start <= 1) {
            System.out.println(thread.getName()+"正在进行“读”操作");
        }
        System.out.println(thread.getName()+"“读”操作完毕");
    }

    /** 写 */
    public synchronized void write(Thread thread) {
        long start = System.currentTimeMillis();
        while(System.currentTimeMillis() - start <= 1) {
            System.out.println(thread.getName()+"正在进行“写”操作");
        }
        System.out.println(thread.getName()+"“写”操作完毕");
    }
}

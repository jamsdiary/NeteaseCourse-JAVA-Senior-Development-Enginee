package com.study.lock.aqs.jucdemo.cyclicbarrier;

import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

// 循环屏障(栅栏)，示例：数据库批量插入
// 游戏大厅... 5人组队打副本
public class CyclicBarrierTest {
    public static void main(String[] args) throws InterruptedException {
        LinkedBlockingQueue<String> sqls = new LinkedBlockingQueue<>();
        // 任务1+2+3...1000  拆分为100个任务（1+..10,  11+20） -> 100线程去处理。

        // 每当有4个线程处于await状态的时候，则会触发barrierAction执行
        CyclicBarrier barrier = new CyclicBarrier(4, new Runnable() {
            @Override
            public void run() {
                // 这是每满足4次数据库操作，就触发一次批量执行
                System.out.println("有4个线程执行了，开始批量插入： " + Thread.currentThread());
                for (int i = 0; i < 4; i++) {
                    System.out.println(sqls.poll());
                }
            }
        });

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    sqls.add("data - " + Thread.currentThread()); // 缓存起来
                    Thread.sleep(1000L); // 模拟数据库操作耗时
                    barrier.await(); // 等待栅栏打开,有4个线程都执行到这段代码的时候，才会继续往下执行
                    System.out.println(Thread.currentThread() + "插入完毕");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }

        Thread.sleep(2000);
    }
}
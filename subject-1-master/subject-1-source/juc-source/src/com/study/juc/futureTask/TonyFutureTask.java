package com.study.juc.futureTask;

import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.LockSupport;

/**
 * 自己实现futureTask -- park/unpark
 */
public class TonyFutureTask<T> implements Runnable {
    Callable<T> callable;
    // callable执行结果
    T result;
    // task执行状态
    String state = "new";
    /**
     * 存储正在等待的消费者
     */
    LinkedBlockingQueue<Thread> waiters = new LinkedBlockingQueue<>();

    public TonyFutureTask(Callable<T> callable) {
        this.callable = callable;
    }

    @Override
    public void run() {
        try {
            result = callable.call();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            state = "end";
        }

        // unpark通知消费者
        System.out.println(Thread.currentThread().getName() + " 生产者执行结束，通知消费者");
        while (true) {
            Thread waiter = waiters.poll();
            if (waiter == null) {
                break;
            }
            LockSupport.unpark(waiter);
        }
    }

    // park / unpark
    public T get() throws Exception {
        Thread mainThread = Thread.currentThread();
        waiters.add(mainThread); // 塞入等待的集合中
        // 判断状态
        System.out.println(Thread.currentThread().getName() + " 消费者进入等待");
        while (!"end".equals(state)) {
            LockSupport.park(mainThread);
        }

        return result;
    }
}

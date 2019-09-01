package com.study.thread.future.service;

import java.util.concurrent.*;
import java.util.concurrent.locks.LockSupport;

// 我们想一想,这个功能怎么实现
// (jdk本质,就是利用一些底层API,为开发人员提供便利)
public class NeteaseFutureTask<T> implements Runnable, Future { // 获取 线程异步执行结果 的方式
    Callable<T> callable; //  业务逻辑在callable里面
    T result = null;
    volatile String state = "NEW";  // task执行状态
    LinkedBlockingQueue<Thread> waiters = new LinkedBlockingQueue<>();// 定义一个存储等待者的集合

    public NeteaseFutureTask(Callable<T> callable) {
        this.callable = callable;
    }

    @Override
    public void run() {
        try {
            result = callable.call();
        } catch (Exception e) {
            e.printStackTrace();
            // result = exception
        } finally {
            state = "END";
        }

        // 唤醒等待者
        Thread waiter = waiters.poll();
        while (waiter != null) {
            LockSupport.unpark(waiter);

            waiter = waiters.poll(); // 继续取出队列中的等待者
        }
    }

    // 返回结果,
    @Override
    public T get() {
        if ("END".equals(state)) {
            return result;
        }

        waiters.offer(Thread.currentThread()); // 加入到等待队列,线程不继续往下执行

        while (!"END".equals(state)) {
            LockSupport.park(); // 线程通信的知识点
        }
        // 如果没有结束,那么调用get方法的线程,就应该进入等待
        return result;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return null;
    }
}

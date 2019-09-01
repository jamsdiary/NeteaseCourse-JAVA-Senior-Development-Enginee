package com.study.lock.aqs;

import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

// 抽象队列同步器
// state， owner， waiters
public class NeteaseAqs {
    // acquire、 acquireShared ： 定义了资源争用的逻辑，如果没拿到，则等待。
    // tryAcquire、 tryAcquireShared ： 实际执行占用资源的操作，如何判定一个由使用者具体去实现。
    // release、 releaseShared ： 定义释放资源的逻辑，释放之后，通知后续节点进行争抢。
    // tryRelease、 tryReleaseShared： 实际执行资源释放的操作，具体的AQS使用者去实现。

    // 1、 如何判断一个资源的拥有者
    public volatile AtomicReference<Thread> owner = new AtomicReference<>();
    // 保存 正在等待的线程
    public volatile LinkedBlockingQueue<Thread> waiters = new LinkedBlockingQueue<>();
    // 记录资源状态
    public volatile AtomicInteger state = new AtomicInteger(0);

    // 共享资源占用的逻辑，返回资源的占用情况
    public int tryAcquireShared(){
        throw new UnsupportedOperationException();
    }

    public void acquireShared(){
        boolean addQ = true;
        while(tryAcquireShared() < 0) {
            if (addQ) {
                // 没拿到锁，加入到等待集合
                waiters.offer(Thread.currentThread());
                addQ = false;
            } else {
                // 阻塞 挂起当前的线程，不要继续往下跑了
                LockSupport.park(); // 伪唤醒，就是非unpark唤醒的
            }
        }
        waiters.remove(Thread.currentThread()); // 把线程移除
    }

    public boolean tryReleaseShared(){
        throw new UnsupportedOperationException();
    }

    public void releaseShared(){
        if (tryReleaseShared()) {
            // 通知等待者
            Iterator<Thread> iterator = waiters.iterator();
            while (iterator.hasNext()) {
                Thread next = iterator.next();
                LockSupport.unpark(next); // 唤醒
            }
        }
    }

    // 独占资源相关的代码

    public boolean tryAcquire() { // 交给使用者去实现。 模板方法设计模式
        throw new UnsupportedOperationException();
    }

    public void acquire() {
        boolean addQ = true;
        while (!tryAcquire()) {
            if (addQ) {
                // 没拿到锁，加入到等待集合
                waiters.offer(Thread.currentThread());
                addQ = false;
            } else {
                // 阻塞 挂起当前的线程，不要继续往下跑了
                LockSupport.park(); // 伪唤醒，就是非unpark唤醒的
            }
        }
        waiters.remove(Thread.currentThread()); // 把线程移除
    }

    public boolean tryRelease() {
        throw new UnsupportedOperationException();
    }

    public void release() { // 定义了 释放资源之后要做的操作
        if (tryRelease()) {
            // 通知等待者
            Iterator<Thread> iterator = waiters.iterator();
            while (iterator.hasNext()) {
                Thread next = iterator.next();
                LockSupport.unpark(next); // 唤醒
            }
        }
    }

    public AtomicInteger getState() {
        return state;
    }

    public void setState(AtomicInteger state) {
        this.state = state;
    }
}

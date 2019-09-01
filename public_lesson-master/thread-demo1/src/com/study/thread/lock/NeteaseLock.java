package com.study.thread.lock;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

// 由一个锁的面试题：--状态； 自己来实现一把Lock锁
// 实现一把自己的锁， 就需要对多线程的知识进行掌握
public class NeteaseLock implements Lock {
    // 需要一个标志。来判断是否有人拿到锁
    AtomicReference<Thread> owner = new AtomicReference<>();
    // 集合 来存储我们的等待线程的信息
    LinkedBlockingQueue<Thread> waiters = new LinkedBlockingQueue<>();

    // 谈恋爱，一把定输赢
    @Override
    public boolean tryLock() {
        // cas 修改(内存操作)
        return owner.compareAndSet(null, Thread.currentThread());
    }

    // r如果拿不到锁，我会一直等待
    @Override
    public void lock() {
        boolean park = false;
        while (!tryLock()) {
            if (!park) {
                // 加入等待集合
                waiters.offer(Thread.currentThread());
                park = true;
            } else {
                LockSupport.park(); // 进入 等待许可
            }
        }
        waiters.remove(Thread.currentThread());
    }

    @Override
    public void unlock() {
        // 释放锁之后，要通知等待者
        if (owner.compareAndSet(Thread.currentThread(), null)) {
            // 遍历等待者，通知继续执行
            Thread next = null;
            while ((next = waiters.peek()) != null) {
                LockSupport.unpark(next);
            }
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}

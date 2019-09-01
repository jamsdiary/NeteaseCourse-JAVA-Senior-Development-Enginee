package com.study.lock.aqs.jucdemo.readWriteLock;

import com.study.lock.aqs.AQSdemo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

// 读写锁
public class NeteaseReadWriteLock implements ReadWriteLock {
    AQSdemo aqSdemo = new AQSdemo() {
        @Override
        public boolean tryAcquire() {
            // 有读的时候，不能写
            if (aqSdemo.getState().get() != 0) {
                return false;
            } else {
                return owner.compareAndSet(null, Thread.currentThread());
            }
        }

        @Override
        public boolean tryRelease() {
            return owner.compareAndSet(Thread.currentThread(), null);
        }

        @Override
        public boolean tryReleaseShared() {
            return aqSdemo.getState().decrementAndGet() >= 0;
        }

        // 加读锁
        @Override
        public int tryAcquireShared() {
            // 如果当前有线程占用了写锁，则不允许再加锁，除非是同一个线程
            if (owner.get() != null && !owner.get().equals(Thread.currentThread())) {
                return -1;
            }
            return aqSdemo.getState().incrementAndGet();
        }
    };

    @Override
    public Lock readLock() {
        return new Lock() {
            @Override
            public void lock() {
                aqSdemo.acquireShared();
            }

            @Override
            public void lockInterruptibly() throws InterruptedException {

            }

            @Override
            public boolean tryLock() {
                return false;
            }

            @Override
            public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
                return false;
            }

            @Override
            public void unlock() {
                aqSdemo.releaseShared();
            }

            @Override
            public Condition newCondition() {
                return null;
            }
        };
    }

    @Override
    public Lock writeLock() {
        return new Lock() {
            @Override
            public void lock() {
                aqSdemo.acquire();
            }

            @Override
            public void lockInterruptibly() throws InterruptedException {

            }

            @Override
            public boolean tryLock() {
                return aqSdemo.tryAcquire();
            }

            @Override
            public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
                return false;
            }

            @Override
            public void unlock() {
                aqSdemo.release();
            }

            @Override
            public Condition newCondition() {
                return null;
            }
        };
    }
}

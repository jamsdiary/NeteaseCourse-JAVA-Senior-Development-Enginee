package com.study.lock.readWriteLock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

// 缓存示例
public class CacheDataDemo {
    // 创建一个map用于缓存
    private Map<String, Object> map = new HashMap<>();
    private static ReadWriteLock rwl = new ReentrantReadWriteLock();

    public static void main(String[] args) {
        // 1 读取缓存里面的数据
        // cache.query()
        // 2 如果换成没数据,则取数据库里面查询  database.query()
        // 3 查询完成之后,数据塞到塞到缓存里面 cache.put(data)
    }

    public Object get(String id) {
        Object value = null;
        // 首先开启读锁，从缓存中去取
        rwl.readLock().lock();
        try {
            if (map.get(id) == null) {
                // TODO database.query();  全部查询数据库 ,缓存雪崩
                // 必须释放读锁
                rwl.readLock().unlock();
                // 如果缓存中没有释放读锁，上写锁。如果不加锁，所有请求全部去查询数据库，就崩溃了
                rwl.writeLock().lock(); // 所有线程在此处等待  1000  1  999 (在同步代码里面再次检查是否缓存)
                try {
                    // 双重检查，防止已经有线程改变了当前的值，从而出现重复处理的情况
                    if (map.get(id) == null) {
                        // TODO value = ...如果缓存没有，就去数据库里面读取
                    }
                    rwl.readLock().lock(); // 加读锁降级写锁,这样就不会有其他线程能够改这个值，保证了数据一致性
                } finally {
                    rwl.writeLock().unlock(); // 释放写锁@
                }
            }
        } finally {
            rwl.readLock().unlock();
        }
        return value;
    }
}

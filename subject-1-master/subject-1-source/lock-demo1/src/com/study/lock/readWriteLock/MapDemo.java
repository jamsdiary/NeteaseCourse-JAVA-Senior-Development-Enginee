package com.study.lock.readWriteLock;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

// 将hashmap改造一个并发安全的
// 比hashTable的实现，效率高，读取的适合并不会同步执行
public class MapDemo {
    private final Map<String, Object> m = new HashMap<>();
    private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private final Lock r = rwl.readLock();
    private final Lock w = rwl.writeLock();

    public Object get(String key) {
        r.lock(); // 可以同时多个线程获取这把锁
        try {
            return m.get(key);
        } finally {
            r.unlock();
        }
    }

    public Object[] allKeys() {
        r.lock();
        try {
            return m.keySet().toArray();
        } finally {
            r.unlock();
        }
    }

    public Object put(String key, Object value) {
        w.lock(); // 一个线程获取 这把锁
        try {
            return m.put(key, value);
        } finally {
            w.unlock();
        }
    }

    public void clear() {
        w.lock();
        try {
            m.clear();
        } finally {
            w.unlock();
        }
    }
}

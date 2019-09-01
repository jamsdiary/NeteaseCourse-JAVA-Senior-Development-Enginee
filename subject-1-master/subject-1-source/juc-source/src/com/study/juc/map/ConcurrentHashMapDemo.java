package com.study.juc.map;

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapDemo {
    public static void main(String[] args) {
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        for (int i = 0; i < 10; i++) {
            concurrentHashMap.put("a" + i,"1");
        }
        concurrentHashMap.put("b","1");
        concurrentHashMap.put("c","1");
        concurrentHashMap.put("d","1");
        concurrentHashMap.put("e","1");
        concurrentHashMap.put("f","1");
    }
}

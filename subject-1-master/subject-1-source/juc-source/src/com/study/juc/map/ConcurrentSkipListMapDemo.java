package com.study.juc.map;

import java.util.Comparator;
import java.util.concurrent.ConcurrentSkipListMap;


public class ConcurrentSkipListMapDemo {
    public static void main(String[] args) {
        // 可以自己定义比较方式
        ConcurrentSkipListMap<String, String> map = new ConcurrentSkipListMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                // 自定义 key 的比较方式
                return 0;
            }
        });
        map.put("a", "1");
        map.put("c", "3");
        map.put("b", "2");
        System.out.println(map.keySet().toString());
    }
}

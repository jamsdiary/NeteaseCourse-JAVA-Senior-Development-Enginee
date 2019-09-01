package com.study.juc.map;

import java.util.Iterator;
import java.util.Map;

// JDK 7 8 hashmap都要注意
public class ConcurrentModificationDemo {
    public static void main(String[] args) {
        HashMap1_7<String, String> map = new HashMap1_7<>();
        map.put("a", "1");
        map.put("b", "2");
        map.put("c", "3");
        map.put("d", "4");

        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            System.out.println(next);
            map.remove(next.getKey());
            // iterator.remove();
        }

    }
}

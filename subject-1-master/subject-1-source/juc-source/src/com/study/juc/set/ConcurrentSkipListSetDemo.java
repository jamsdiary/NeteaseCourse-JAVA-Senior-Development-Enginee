package com.study.juc.set;

import java.util.Iterator;
import java.util.concurrent.ConcurrentSkipListSet;

public class ConcurrentSkipListSetDemo {
    public static void main(String[] args) {
        // 可设置比较方式
        ConcurrentSkipListSet<String> skipListSet = new ConcurrentSkipListSet<>(String::compareTo);
        skipListSet.add("aa");
        skipListSet.add("ca");
        skipListSet.add("aa");
        skipListSet.add("da");


        Iterator<String> iterator = skipListSet.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }
}

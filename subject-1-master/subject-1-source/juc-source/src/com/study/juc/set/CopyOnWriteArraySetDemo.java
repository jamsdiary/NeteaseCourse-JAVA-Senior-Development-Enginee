package com.study.juc.set;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;

public class CopyOnWriteArraySetDemo {
    public static void main(String[] args) {
        CopyOnWriteArraySet<String> copyOnWriteArraySet = new CopyOnWriteArraySet<>();

        copyOnWriteArraySet.add("aa");
        copyOnWriteArraySet.add("ca");
        copyOnWriteArraySet.add("aa");
        copyOnWriteArraySet.add("da");

        Iterator<String> iterator = copyOnWriteArraySet.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }
}

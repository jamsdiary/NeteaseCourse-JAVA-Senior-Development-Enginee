package com.study.jvm;

import java.util.ArrayList;

// 资源占用过多或者资源未释放，内存溢出
// 网易碰到问题
public class OutOfMemoryDemo1 {
    static ArrayList<Object> space = new ArrayList<Object>();

    public static void main(String[] args) throws Exception {
        // 内存泄漏 最终会导致  内存溢出
        for (int i = 0; i < 1000; i++) {
            space.add(new byte[1024 * 1024 * 64]); // 64兆
            Thread.sleep(3000L);
        }
    }
}

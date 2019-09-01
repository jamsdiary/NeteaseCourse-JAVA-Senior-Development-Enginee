package com.study.jvm;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Event;
import com.dianping.cat.message.Transaction;

import java.util.concurrent.TimeUnit;

// 频繁调用system.gc导致fullgc次数过多
// 使用server模式运行 开启GC日志
// -Xmx512m -server -verbose:gc -XX:+PrintGCDetails -Xloggc:filepath -XX:+HeapDumpOnOutOfMemoryError
public class FullGCDemo1 {
    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 1000; i++) {
            byte[] tmp = new byte[1024 * 1024 * 256]; // 256兆
            System.gc(); // 8G堆 128兆。full GC
            System.out.println("我GC一次了");
            Thread.sleep(2000L);
        }
    }
}

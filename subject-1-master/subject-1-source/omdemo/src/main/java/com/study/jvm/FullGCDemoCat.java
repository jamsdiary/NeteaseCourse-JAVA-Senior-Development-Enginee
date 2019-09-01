package com.study.jvm;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Event;
import com.dianping.cat.message.Transaction;

// 频繁调用system.gc导致fullgc次数过多
// 使用server模式运行 开启GC日志
// -Xmx512m -server -verbose:gc -XX:+PrintGCDetails
public class FullGCDemoCat {
    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 1000; i++) {
            // 大众点评的cat简单示例
            Transaction t = Cat.newTransaction("URL", "pageName");
            try {
                Cat.logEvent("URL.Server", "192.168.100.242", Event.SUCCESS, "ip=192.168.137.1");
                Cat.logMetricForCount("metric.key");
                Cat.logMetricForDuration("metric.key", 5);
                Thread.sleep(3000L);

                t.setStatus(Transaction.SUCCESS);
            } catch (Exception e) {
                t.setStatus(e);
                Cat.logError(e);
            } finally {
                t.complete();
            }
            // 结束
            byte[] tmp = new byte[1024 * 1024 * 256]; // 256兆
            System.gc();
            System.out.println("我GC一次了");
            Thread.sleep(2000L);
        }
    }
}

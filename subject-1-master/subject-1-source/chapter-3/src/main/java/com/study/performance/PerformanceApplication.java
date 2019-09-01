package com.study.performance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// 启动程序，模拟用户请求
// 每100毫秒钟创建1000线程，每个线程创建一个512kb的对象，最多100毫秒内同时存在1000线程，并发量1000/s，吞吐量6000/s，查看GC的情况
@SpringBootApplication
public class PerformanceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PerformanceApplication.class, args);
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
            new Thread(() -> {
                for (int i = 0; i < 1000; i++) {
                    try {
                        //  不干活，专门512kb的小对象
                        byte[] temp = new byte[1024 * 512];
                        Thread.sleep(new Random().nextInt(100)); // 随机睡眠200毫秒秒以内
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }, 100, 100, TimeUnit.MILLISECONDS);
    }
}

// 打包 mvn clean package
// 服务器上运行 performance-1.0.0.jar
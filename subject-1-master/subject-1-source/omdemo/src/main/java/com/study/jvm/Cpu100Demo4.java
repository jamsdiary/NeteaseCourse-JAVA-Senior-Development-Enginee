package com.study.jvm;

import java.util.Random;

// 线程过多导致的问题(jstack定位)
public class Cpu100Demo4 {
    // 资源：每一个请求,业务执行需要占用多少资源，CPU * 1--> 增加资源。
    // 线程池，控制线程数量，升级更高的配置
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                try {
                    int x = 0;
                    for (int j = 0; j < 10000; j++) {
                        x = x + 1;
                        long random = new Random().nextInt(100);
                        Thread.sleep(random); // 模拟c处理耗时
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
            long random = new Random().nextInt(500);
            Thread.sleep(random); // 模拟接口调用
        }
    }
}

package com.study.jvm;

import java.util.Random;

// 个别线程占用资源过多

public class Cpu100Demo5 {
    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            while (true) {
                new Random().nextInt(100);
            }
        }, "CPU-high").start();
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

package com.study.volatiletest.demo3;

// java 提供了两种方式
public class Test2 {
    static int i = 0, j = 0;

    static synchronized void one() {
        i++;
        j++;
    }

    static synchronized void two() {
        if (i < j) { // 判断的时候，有没有可能出现i小于j的问题？
            System.out.println("i=" + i + " j=" + j);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            for (int k = 0; k < 100000; k++) {
                Test2.one();
            }
        }).start();
        new Thread(() -> {
            for (int k = 0; k < 100000; k++) {
                Test2.two();
            }
        }).start();

        Thread.sleep(10000L);
    }
}

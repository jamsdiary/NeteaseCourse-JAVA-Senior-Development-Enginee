package com.study.volatiletest.demo3;

public class Test {
    static int i = 0, j = 0;

    static void one() {
        i++;
        j++;
    }

    static void two() {
        if (i < j) { // 判断的时候，有没有可能出现i小于j的问题？
            System.out.println("i=" + i + " j=" + j);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            for (int k = 0; k < 100000; k++) {
                Test.one();
            }
        }).start();
        new Thread(() -> {
            for (int k = 0; k < 100000; k++) {
                Test.two();
            }
        }).start();

        Thread.sleep(10000L);
    }
}

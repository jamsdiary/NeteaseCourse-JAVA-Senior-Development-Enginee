package com.study.volatiletest.demo3;

public class Test1 {
    static volatile int i = 0, j = 0;

    static void one() {
        i++; //
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
                Test1.one();
            }
        }).start();
        new Thread(() -> {
            for (int k = 0; k < 100000; k++) {
                Test1.two();
            }
        }).start();

        Thread.sleep(10000L);
    }
}


// 依旧会出现这个问题， volatile保证了主内存中的值 i 一定大于j
// 但是在读取i  和 j 的值的过程中，one方法又被调用了很多次
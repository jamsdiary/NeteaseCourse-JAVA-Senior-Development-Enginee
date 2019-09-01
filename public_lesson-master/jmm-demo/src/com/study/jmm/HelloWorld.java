package com.study.jmm;

public class HelloWorld {
    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            try {
                byte[] array = new byte[1024 * 1024 * 10]; // 10兆
                Thread.sleep(1000 * 60 * 10); //10分钟
                System.out.println(array.length);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}

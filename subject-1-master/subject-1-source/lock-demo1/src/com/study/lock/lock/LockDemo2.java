package com.study.lock.lock;

public class LockDemo2 {
    int i = 0;

    public void add() {
        synchronized (this) {
            i++;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        LockDemo2 ld = new LockDemo2();

        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    ld.add();
                }
            }).start();
        }
        Thread.sleep(2000L);
        System.out.println(ld.i);
    }
}

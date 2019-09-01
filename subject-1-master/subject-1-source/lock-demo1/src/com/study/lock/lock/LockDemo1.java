package com.study.lock.lock;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class LockDemo1 {
    volatile int value = 0;

    static Unsafe unsafe; // 直接操作内存，修改对象，数组内存....强大的API
    private static long valueOffset;

    static {
        try {
            // 反射技术获取unsafe值
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);

            // 获取到 value 属性偏移量（用于定于value属性在内存中的具体地址）
            valueOffset = unsafe.objectFieldOffset(LockDemo1.class
                    .getDeclaredField("value"));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void add() {
        // TODO xx00
        // i++;// JAVA 层面三个步骤
        // CAS + 循环 重试
        int current;
        do {
            // 操作耗时的话， 那么 线程就会占用大量的CPU执行时间
            current = unsafe.getIntVolatile(this, valueOffset);
        } while (!unsafe.compareAndSwapInt(this, valueOffset, current, current + 1));
        // 可能会失败
    }

    public static void main(String[] args) throws InterruptedException {
        LockDemo1 ld = new LockDemo1();

        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    ld.add();
                }
            }).start();
        }
        Thread.sleep(2000L);
        System.out.println(ld.value);
    }
}

package com.study.lock.cas;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

// aba 问题
// 重复操作 / 过时操作。
public class AbaDemo {
    // 模拟充值
    // 有3个线程在给用户充值，当用户余额少于20时，就给用户充值20元。
    // 有100个线程在消费，每次消费10元。用户初始有9元
    static AtomicInteger money = new AtomicInteger(19);

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                int current;
                do {
                    current = money.get();// 获取当前内存中的值
                } while (!money.compareAndSet(current, current + 20)); // CAS
            }).start();
        }

        new Thread(() -> {

            for (int i = 0; i < 100; i++) {
                while (true) {
                    Integer m = money.get();
                    if (m > 10) {
                        if (money.compareAndSet(m, m - 10)) {
                            System.out.println("消费10元，余额:" + money.get());
                            break;
                        }
                    } else {
                        break;
                    }
                }
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }

        }).start();
    }
}

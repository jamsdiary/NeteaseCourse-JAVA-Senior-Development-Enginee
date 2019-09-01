package com.study.lock.cas;

import java.util.concurrent.atomic.AtomicStampedReference;

// aba 问题
// 重复操作 / 过时操作。
public class AbaDemo1 {
    // 模拟充值
    // 有3个线程在给用户充值，当用户余额少于20时，就给用户充值20元。
    // 有100个线程在消费，每次消费10元。用户初始有9元
    static AtomicStampedReference<Integer> money = new AtomicStampedReference<Integer>(19, 0);

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            final int timestamp = money.getStamp();
            new Thread(() -> {
                while (true) {
                    while (true) {
                        Integer m = money.getReference();
                        if (m < 20) {
                            if (money.compareAndSet(m, m + 20, timestamp,
                                    timestamp + 1)) {
                                System.out.println("充值成功，余额:"
                                        + money.getReference());
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                }
            }).start();
        }

        new Thread(() -> {

            for (int i = 0; i < 100; i++) {
                while (true) {
                    int timestamp = money.getStamp();
                    Integer m = money.getReference();
                    if (m > 10) {
                        if (money.compareAndSet(m, m - 10, timestamp,
                                timestamp + 1)) {
                            System.out.println("消费10元，余额:"
                                    + money.getReference());
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

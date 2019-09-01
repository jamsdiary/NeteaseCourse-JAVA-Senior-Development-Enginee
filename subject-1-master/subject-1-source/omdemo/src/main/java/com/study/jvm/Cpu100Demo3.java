package com.study.jvm;

// 活锁
public class Cpu100Demo3 {
    /**
     * 包子店
     */
    public static Object baozidian = null;

    /**
     * 会导致程序永久等待的wait/notify
     */
    public void waitNotifyDeadLockTest() throws Exception {
        // 启动消费者线程
        new Thread(() -> {
            if (baozidian == null) { // 如果没包子，则进入等待
                try {
                    Thread.sleep(5000L);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                synchronized (this) {
                    try {
                        System.out.println("1、进入等待，线程ID为： " + Thread.currentThread().getId());
                        this.wait(); // 多次查看
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("2、买到包子，回家");
        }).start();
        // 3秒之后，生产一个包子
        Thread.sleep(3000L);
        baozidian = new Object();
        synchronized (this) {
            this.notifyAll();
            System.out.println("3、通知消费者");
        }
    }

    public static void main(String[] args) throws Exception {
        new Cpu100Demo3().waitNotifyDeadLockTest();
    }
}

package com.study.juc.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

// 它是基于链表的队列，此队列按 FIFO（先进先出）排序元素。
// 如果有阻塞需求，用这个。类似生产者消费者场景
public class LinkedBlockingQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        // 构造时可以指定容量，默认Integer.MAX_VALUE
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<String>(3);
        // 1秒消费数据一个
        new Thread(() -> {
            while (true) {
                try {
                    System.out.println("取到数据：" + queue.poll()); // poll非阻塞
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                }
            }
        }).start();

        Thread.sleep(3000L); // 让前面的线程跑起来

        // 三个线程塞数据
        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                try {
                    // queue.put(Thread.currentThread().getName()); // put阻塞
                    queue.offer(Thread.currentThread().getName()); // offer非阻塞，满了返回false
                    System.out.println(Thread.currentThread() + "塞入完成");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}

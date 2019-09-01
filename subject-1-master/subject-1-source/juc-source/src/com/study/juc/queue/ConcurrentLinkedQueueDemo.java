package com.study.juc.queue;

import java.util.concurrent.ConcurrentLinkedQueue;

// 优势：无锁。
// 注意：批量操作不提供原子保证  addAll, removeAll, retainAll, containsAll, equals, and toArray
// 坑： size()方法每次都是遍历整个链表，最好不要频繁调用
// 如果没有阻塞要求，用这个挺好的（堆积数据）
public class ConcurrentLinkedQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        // 不需要指定容量，addAll
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<String>();
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
                    queue.offer(Thread.currentThread().getName()); // offer非阻塞，满了返回false
                    System.out.println(Thread.currentThread() + "塞入完成");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}

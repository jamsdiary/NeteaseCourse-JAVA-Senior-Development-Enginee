package com.study.juc.queue;

import java.util.concurrent.ArrayBlockingQueue;


// 它是基于数组的阻塞循环队列， 此队列按 FIFO（先进先出）原则对元素进行排序。
public class ArrayBlockingQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        // 构造时需要指定容量(量力而行),可以选择是否需要公平（最先进入阻塞的，先操作）
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(3, false);
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
                     queue.put(Thread.currentThread().getName()); // put阻塞(如果当前的队列已经塞满了数据，线程不会继续往下执行，等待其他线程把
                    // 队列的数据拿出去// )
//                    queue.offer(Thread.currentThread().getName()); // offer非阻塞，满了返回false
                    System.out.println(Thread.currentThread() + "塞入完成");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}

package com.study.juc.queue;

import java.util.concurrent.SynchronousQueue;

// 这是一个神奇的队列， 因为他不存数据。 手把手的交互数据
public class SynchronousQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        SynchronousQueue<String> synchronousQueue = new SynchronousQueue<>();
        // synchronousQueue.add("a"); // IllegalStateException
        // synchronousQueue.offer("a");
        System.out.println(synchronousQueue.poll()); // 非阻塞

        // 阻塞式的用法
        new Thread(() -> {
            try {
                System.out.println("等数据....");
                System.out.println(synchronousQueue.take());
                System.out.println("执行完毕....");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        Thread.sleep(1000L);
        System.out.println("准备赛数据了");
        synchronousQueue.put("a");// 等待有人取走他
    }
}

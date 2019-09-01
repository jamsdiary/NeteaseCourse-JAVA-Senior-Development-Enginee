package com.study.juc.queue;

import java.util.PriorityQueue;
import java.util.concurrent.PriorityBlockingQueue;

// 包装了 PriorityQueue
// 是一个带优先级的 队列，而不是先进先出队列。
// 元素按优先级顺序被移除，该队列也没有上限
// 没有容量限制的，自动扩容
// 虽然此队列逻辑上是无界的，但是由于资源被耗尽，所以试图执行添加操作可能会导致 OutOfMemoryError），
// 但是如果队列为空，
// 那么取元素的操作take就会阻塞，所以它的检索操作take是受阻的。另外，
// 入该队列中的元素要具有比较能力
public class PriorityBlockingQueueDemo {
    public static void main(String[] args) {
        // 可以设置比对方式
        PriorityBlockingQueue<String> priorityQueue = new PriorityBlockingQueue<>(2);
        priorityQueue.add("c");
        priorityQueue.add("a");
        priorityQueue.add("b");

        System.out.println(priorityQueue.poll());
        System.out.println(priorityQueue.poll());
        System.out.println(priorityQueue.poll());
    }
}

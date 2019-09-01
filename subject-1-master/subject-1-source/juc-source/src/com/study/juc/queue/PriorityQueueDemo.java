package com.study.juc.queue;

import java.util.Comparator;
import java.util.PriorityQueue;

// 是一个带优先级的 队列，而不是先进先出队列。
// 元素按优先级顺序被移除，该队列也没有上限
// 没有容量限制的，自动扩容
// 虽然此队列逻辑上是无界的，但是由于资源被耗尽，所以试图执行添加操作可能会导致 OutOfMemoryError），
// 但是如果队列为空，
// 那么取元素的操作take就会阻塞，所以它的检索操作take是受阻的。另外，
// 入该队列中的元素要具有比较能力
public class PriorityQueueDemo {
    public static void main(String[] args) {
        // 可以设置比对方式
        PriorityQueue<String> priorityQueue = new PriorityQueue<>(new Comparator<String>() {
            @Override //
            public int compare(String o1, String o2) {
                // 实际就是 元素之间的 比对。
                return 0;
            }
        });
        priorityQueue.add("c");
        priorityQueue.add("a");
        priorityQueue.add("b");

        System.out.println(priorityQueue.poll());
        System.out.println(priorityQueue.poll());
        System.out.println(priorityQueue.poll());

        PriorityQueue<MessageObject> MessageObjectQueue = new PriorityQueue<>(new Comparator<MessageObject>() {
            @Override
            public int compare(MessageObject o1, MessageObject o2) {
                return o1.order > o2.order ? -1 : 1;
            }
        });
    }
}

class MessageObject {
    String content;
    int order;
}

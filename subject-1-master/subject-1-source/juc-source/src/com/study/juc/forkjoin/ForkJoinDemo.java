package com.study.juc.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

// 分而治之的理念
public class ForkJoinDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 默认情况下，并行线程数量等于可用处理器的数量
        // ForkJoinPool与其他类型的ExecutorService的区别主要在于它使用了工作窃取:
        // 池中的所有线程都试图查找和执行提交给池的任务和/或其他活动任务创建的任务
        // (如果不存在工作，则最终阻塞等待工作)。
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        RecursiveTask<String> recursiveTask = new RecursiveTask<String>() {
            @Override
            protected String compute() {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(toString() + "" + Thread.currentThread());
                ForkJoinTask<String> newTask = this.fork();
                newTask.join();
                System.out.println("执行结束");
                return "";
            }
        };

        ForkJoinTask<String> submit = forkJoinPool.submit(recursiveTask);
        ForkJoinTask<String> submitx = forkJoinPool.submit(recursiveTask);
        System.out.println(submit.get());

        recursiveTask.join();
    }
}

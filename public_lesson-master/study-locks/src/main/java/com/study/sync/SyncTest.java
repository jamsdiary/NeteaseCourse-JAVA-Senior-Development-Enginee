package com.study.sync;

import java.util.ArrayList;

/**
 * @Auther: allen
 * @Date: 2018/12/2 12:21
 */
public class SyncTest {

    public static void main(String[] args) {
        final InsertData insertData = new InsertData();

        new Thread() {
            @Override
            public void run() {
                insertData.insert(Thread.currentThread());
            }
        }.start();


        new Thread() {
            @Override
            public void run() {
                insertData.insert(Thread.currentThread());
            }
        }.start();
    }
}

class InsertData {
    private ArrayList<Integer> arrayList = new ArrayList<Integer>();

    // 没有synchronized，两个线程在同时执行insert方法
    //public void insert(Thread thread) {

    // 加了后是顺序执行insert方法
    public synchronized void insert(Thread thread) {
        for (int i = 0; i < 5; i++) {
            System.out.println(thread.getName() + "在插入数据" + i);
            arrayList.add(i);
        }
    }
}

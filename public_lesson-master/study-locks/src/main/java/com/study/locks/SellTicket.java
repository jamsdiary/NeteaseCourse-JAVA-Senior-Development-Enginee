package com.study.locks;

/**
 * 模拟多线程下资源竞争
 *
 * @Auther: allen
 * @Date: 2018/12/2 11:05
 */
public class SellTicket implements Runnable {
    // 100张票
    private int tickets = 100;

    @Override
    public void run() {
        while (tickets > 0) {

            if (tickets > 0) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "正在出售第 " + tickets-- + " 张票");
            }
        }
    }
}

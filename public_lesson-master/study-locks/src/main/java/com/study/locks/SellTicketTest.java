package com.study.locks;

/**
 * @Auther: allen
 * @Date: 2018/12/2 11:05
 */
public class SellTicketTest {
    public static void main(String[] args) {
        // SellTicket st = new SellTicket();
        // SellTicket_Sync st = new SellTicket_Sync();
        SellTicket_Redisson st = new SellTicket_Redisson();
        // 三个线程模拟三个窗口售票
        for (int i=1; i<=3; i++) {
            new Thread(st, "窗口" + i).start();
        }
    }
}

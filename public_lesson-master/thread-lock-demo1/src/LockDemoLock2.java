import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// 多线程 原子性
public class LockDemoLock2 {
    volatile int i = 0;

    // 广泛应用在JUC并发编程包
    // Lock lock = new ReentrantLock();
    Lock lock = new NeteaseLock();

    public void add() {
        lock.lock(); // 获取锁（一直等，直到获取到锁）
        try {
            i++;
        } finally {
            lock.unlock(); // 释放锁
        }

    }

    public static void main(String[] args) throws InterruptedException {
        LockDemoLock2 ld = new LockDemoLock2();

        // 期望20000
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    ld.add();
                }
            }).start();
        }
        Thread.sleep(2000L);
        System.out.println(ld.i);
    }
}

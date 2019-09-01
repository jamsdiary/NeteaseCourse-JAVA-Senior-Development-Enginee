import java.util.concurrent.atomic.AtomicInteger;

// j.u.c
public class LockDemo1 {
    // volatile int i = 0;
    AtomicInteger i = new AtomicInteger(0);

    public void add() {
        // i++; // i+1
        i.incrementAndGet();// 对i变量进行加1操作 并返回i
    }

    public static void main(String[] args) throws InterruptedException {
        LockDemo1 ld = new LockDemo1();

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

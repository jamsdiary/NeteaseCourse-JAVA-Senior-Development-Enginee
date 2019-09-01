// 多线程 原子性
public class LockDemoLock1 {
    volatile int i = 0;

    public void add() {
        synchronized (this) { // 同步关键字
            i++; // i+1
            // j ++
            // n ++
        }
    }

    public static void main(String[] args) throws InterruptedException {
        LockDemoLock1 ld = new LockDemoLock1();

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

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;

// j.u.c
public class LockDemoCas {
    volatile int value = 0;

    static Unsafe unsafe;
    static long valueOffset;

    static {
        try {
            // 反射
            Field declaredField = Unsafe.class.getDeclaredField("theUnsafe");
            declaredField.setAccessible(true);
            unsafe = (Unsafe) declaredField.get(null);

            // 修改 value，不是通过普通的方式去修改
            // 属性的偏移量 -- 用它定位内存中 一个对象内  具体属性的内存位置
            valueOffset = unsafe.objectFieldOffset(LockDemoCas.class.getDeclaredField("value"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void add() {
        // value ++;
        // 通过CAS去操作
        int current;
        do {
            current = unsafe.getIntVolatile(this, valueOffset);
        } while (!unsafe.compareAndSwapInt(this, valueOffset, current, current + 1));
    }

    public static void main(String[] args) throws InterruptedException {
        LockDemoCas ld = new LockDemoCas();

        // 期望20000
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    ld.add();
                }
            }).start();
        }
        Thread.sleep(2000L);
        System.out.println(ld.value);
    }
}

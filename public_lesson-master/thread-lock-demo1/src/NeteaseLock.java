import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

// 我和你一起 写Lock接口实现
public class NeteaseLock implements Lock {
    // 标志：谁拿到了锁
    AtomicReference<Thread> owner = new AtomicReference<>();
    // 集合 -- 存储正在等待的线程
    public LinkedBlockingQueue<Thread> waiters = new LinkedBlockingQueue<>();

    @Override
    public void lock() {
        // thread1进入等待锁的阶段   t2拿到锁
        while (!owner.compareAndSet(null, Thread.currentThread())) {
            // 没拿到锁，线程需要等待，其他线程释放锁
            waiters.add(Thread.currentThread()); // 加入到等待集合中
            // ？ 等待，线程不继续执行 park unpark
            LockSupport.park(); // 让currentThread线程  等待    thread-1
            waiters.remove(Thread.currentThread());
        }
        // 拿到锁之后执行接下来的代码
    }

    @Override
    public void unlock() {
        if (owner.compareAndSet(Thread.currentThread(), null)) {
            // 释放锁之后，告知其他线程，你们可以继续抢夺锁
            Object[] objects = waiters.toArray();
            for (Object object : objects) { // 遍历  拿出来所有的等待线程
                Thread next = (Thread) object;
                LockSupport.unpark(next); // 通知线程继续执行   t2 唤醒其他线程
            }
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }


    @Override
    public Condition newCondition() {
        return null;
    }
}

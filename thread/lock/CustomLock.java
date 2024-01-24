package thread.lock;

import sun.misc.Unsafe;
import thread.cas.UnsafeSupport;
import thread.pool.ThreadObjectPool;
import util.Print;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Customize a lock
 *
 * @author zqw
 * @date 2021/6/24
 */
class CustomLock {

    volatile int status = 0;

    private static long stateOffset;

    private static Unsafe unsafe = null;

    static ExecutorService pool = ThreadObjectPool.newFixedThreadPool(5, 10, 5);

    static {
        try {
            unsafe = UnsafeSupport.reflectGetUnsafe();
            if (unsafe == null) {
                throw new RuntimeException("unsafe is null");
            }
            stateOffset = unsafe.objectFieldOffset(
                    CustomLock.class.getDeclaredField("status"));
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

    }

    void lock() {
        while (!compareAndSet()) {
            System.out.println(Thread.currentThread().getName() + " cas...");
        }
    }

    boolean compareAndSet() {
        return unsafe.compareAndSwapInt(this, stateOffset, 0, 1);
    }

    void unlock() {
        status = 0;
    }

    public static void main(String[] args) {
        CustomLock cl = new CustomLock();
        pool.execute(() -> {
            cl.lock();
            System.out.println("t1...");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException ex) {
                Print.err(ex.getMessage());
            }
            System.out.println("t1 release the lock");
            cl.unlock();
        });

        pool.execute(() -> {
            cl.lock();
            System.out.println("t2...");
            cl.unlock();
        });
        pool.shutdown();

    }
}

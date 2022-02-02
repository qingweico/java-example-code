package thread;

import sun.misc.Unsafe;
import thread.pool.CustomThreadPool;

import java.lang.reflect.Field;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Customize a lock
 *
 * @author zqw
 * @date 2021/6/24
 */
public class CustomLock {

    volatile int status = 0;

    private static long stateOffset;

    private static Unsafe unsafe = null;

    static ExecutorService pool = CustomThreadPool.newFixedThreadPool(5, 10, 5);

    static {
        Field singletonInstanceField;
        try {
            singletonInstanceField = Unsafe.class.getDeclaredField("theUnsafe");
            singletonInstanceField.setAccessible(true);
            unsafe = (Unsafe) singletonInstanceField.get(null);

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
                ex.printStackTrace();
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

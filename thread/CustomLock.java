package thread;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

/**
 *
 * Customize a lock
 * @author:qiming
 * @date: 2021/6/24
 */
public class CustomLock {

    volatile int status;

    private static long stateOffset;

    private static Unsafe unsafe = null;

    static {
        Field singletonInstanceField;
        try {
            singletonInstanceField = Unsafe.class.getDeclaredField("theUnsafe");
            singletonInstanceField.setAccessible(true);
            unsafe = (Unsafe) singletonInstanceField.get(null);

            stateOffset = unsafe.objectFieldOffset(
                    CustomLock.class.getDeclaredField("status"));
        }catch(Exception ex) {
            System.err.println(ex.getMessage());
        }

    }

    void lock() {
        while(!compareAndSet()) {
            // System call
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
        new Thread(() -> {
            cl.lock();
            try {
                TimeUnit.SECONDS.sleep(2);
            }catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            System.out.println("t1...");
            cl.unlock();
        }, "t1").start();

        try {
            TimeUnit.MILLISECONDS.sleep(100);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            cl.lock();
            System.out.println("t2...");
            cl.unlock();
        }, "t2").start();

    }
}

package thread.lock;

import thread.pool.ThreadObjectPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Reenter-able synchronized:
 * A task can acquire an object's lock multiple times.
 *
 * @author zqw
 * @date 2021/4/10
 */
class ReenterableSynchronized {
    final Object lock = new Object();
    static ExecutorService pool = ThreadObjectPool.newFixedThreadPool(2);

    public void f() {
        // countLock++
        synchronized (lock) {
            System.out.println("f()");
            // countLock++
            f2();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // After 1 second, the lock is completely released
            // and the resource can be used by other tasks.
        }
    }

    public void f2() {
        synchronized (lock) {
            System.out.println("f2()");
        }

    }

    public static void main(String[] args) {
        ReenterableSynchronized execute = new ReenterableSynchronized();

        pool.execute(execute::f);
        pool.execute(execute::f);

        pool.shutdown();
    }
}

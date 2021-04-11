package thread;

import java.util.concurrent.TimeUnit;

/**
 * Reenterable synchronized:
 * A task can acquire an object's lock multiple times.
 *
 * @author:qiming
 * @date: 2021/4/10
 */
public class ReenterableSynchronized {
    final Object lock = new Object();

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

        Thread t1 = new Thread(execute::f);
        Thread t2 = new Thread(execute::f);

        t1.start();
        t2.start();
    }
}
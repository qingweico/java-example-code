package thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static util.Print.printnb;

/**
 * Use three threads to alternate output ABC...
 *
 * @author:qiming
 * @date: 2021/1/30
 */
public class LockCondition {

    private static final ReentrantLock lock = new ReentrantLock();

    private static final Condition cA = lock.newCondition();
    private static final Condition cB = lock.newCondition();
    private static final Condition cC = lock.newCondition();

    private static final CountDownLatch latchB = new CountDownLatch(1);
    private static final CountDownLatch latchC = new CountDownLatch(1);

    private static final int COUNT = 10;

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            lock.lock();
            try {
                for (int i = 0; i < COUNT; i++) {
                    printnb("A");
                    cB.signal();
                    if (i == 0) {
                        latchB.countDown();
                    }
                    cA.await();
                }
                cB.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

        });

        Thread t2 = new Thread(() -> {
            try {
                latchB.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.lock();
            try {
                for (int i = 0; i < COUNT; i++) {
                    printnb("B");
                    cC.signal();
                    if (i == 0) {
                        latchC.countDown();
                    }
                    cB.await();
                }
                cC.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

        });

        Thread t3 = new Thread(() -> {
            try {
                latchC.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.lock();
            try {
                for (int i = 0; i < COUNT; i++) {
                    printnb("C");
                    cA.signal();
                    cC.await();
                }
                cA.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

        });

        t1.start();
        t2.start();
        t3.start();


    }
}

package thread.lock;

import thread.pool.ThreadObjectPool;
import util.Print;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static util.Print.print;

/**
 * Use three threads to alternate output ABC...
 *
 * @author zqw
 * @date 2021/1/30
 */
class LockCondition {

    static ReentrantLock lock = new ReentrantLock();

    static Condition cA = lock.newCondition();
    static Condition cB = lock.newCondition();
    static Condition cC = lock.newCondition();

    static CountDownLatch latchB = new CountDownLatch(1);
    static CountDownLatch latchC = new CountDownLatch(1);
    static ExecutorService pool = ThreadObjectPool.newFixedThreadPool(3, 3, 1);

    private static final int COUNT = 10;

    public static void main(String[] args) {
        pool.execute(() -> {
            lock.lock();
            try {
                for (int i = 0; i < COUNT; i++) {
                    print("A");
                    cB.signal();
                    if (i == 0) {
                        latchB.countDown();
                    }
                    cA.await();
                }
                cB.signal();
            } catch (InterruptedException e) {
                Print.err(e.getMessage());
            } finally {
                lock.unlock();
            }

        });

        pool.execute(() -> {
            try {
                latchB.await();
            } catch (InterruptedException e) {
                Print.err(e.getMessage());
            }
            lock.lock();
            try {
                for (int i = 0; i < COUNT; i++) {
                    print("B");
                    cC.signal();
                    if (i == 0) {
                        latchC.countDown();
                    }
                    cB.await();
                }
                cC.signal();
            } catch (InterruptedException e) {
                Print.err(e.getMessage());
            } finally {
                lock.unlock();
            }

        });

        pool.execute(() -> {
            try {
                latchC.await();
            } catch (InterruptedException e) {
                Print.err(e.getMessage());
            }
            lock.lock();
            try {
                for (int i = 0; i < COUNT; i++) {
                    print("C");
                    cA.signal();
                    cC.await();
                }
                cA.signal();
            } catch (InterruptedException e) {
                Print.err(e.getMessage());
            } finally {
                lock.unlock();
            }
        });
        pool.shutdown();
    }
}

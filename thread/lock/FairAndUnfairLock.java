package thread.lock;

import thread.pool.ThreadObjectPool;
import util.Print;
import util.constants.Constants;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.ReentrantLock;

/**
 * {@link AbstractQueuedSynchronizer}
 *
 * @author zqw
 * @date 2022/6/25
 */
public class FairAndUnfairLock {
    static ExecutorService pool = ThreadObjectPool.newFixedThreadPool(3, true);
    static Integer ticketCount = 100;
    static Integer threadCount = 3;
    static boolean isFair = false;
    static CountDownLatch latch = new CountDownLatch(threadCount);
    static ReentrantLock lock = new ReentrantLock(isFair);

    public static void sail() {
        lock.lock();
        try {
            // 不可以使用while
            if (ticketCount > 0) {
                System.out.println(Thread.currentThread().getName() + ":" + " 卖出第[ " + ticketCount-- + " ], 还剩 [ " + ticketCount + " ]");
            } else {
                System.out.println(Thread.currentThread().getName() + ": 票已卖完!");
            }
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        var start = System.currentTimeMillis();
        for (int i = 0; i < threadCount; i++) {
            pool.execute(() -> {
                for (int j = 0; j < Constants.NUM_100; j++) {
                    sail();
                }
                latch.countDown();
            });
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            Print.err(e.getMessage());
        }
        var ret = System.currentTimeMillis() - start;
        if (isFair) {
            Print.time("[Fair Lock] cost time", ret);
        } else {
            Print.time("[Unfair Lock] cost time", ret);
        }
        pool.shutdown();
    }
}

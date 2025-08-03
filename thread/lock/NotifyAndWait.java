package thread.lock;


import cn.qingweico.concurrent.pool.ThreadObjectPool;
import cn.qingweico.io.Print;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

/**
 * @author zqw
 * @date 2020/10/23
 * <p>
 * Print ABCDEF 123456 alternately
 */
class NotifyAndWait {
    /**
     * Let the t2 thread run first
     */
    private static volatile boolean t2Start = false;

    static CountDownLatch latch = new CountDownLatch(1);
    static ExecutorService pool = ThreadObjectPool.newFixedThreadPool(2, 2, 1);

    public static void main(String[] args) {
        final Object o = new Object();

        char[] numbers = "123456".toCharArray();
        char[] words = "ABCDEF".toCharArray();

        pool.execute(() -> {
            try {
                // The thread on CountDownLatch gets blocked in await().
                latch.await();
            } catch (InterruptedException e) {
                Print.err(e.getMessage());
            }
            synchronized (o) {
                while (!t2Start) {
                    try {
                        o.wait();
                    } catch (InterruptedException e) {
                        Print.err(e.getMessage());
                    }
                }
                for (char n : numbers) {
                    System.out.print(n);
                    try {
                        // notify: Wakes up a thread in the current waiting queue.
                        // notifyAll: Wakes up all threads in the current waiting queue.
                        o.notify();
                        // Block the current thread to allow the lock, to enter the wait queue.
                        o.wait();
                    } catch (InterruptedException e) {
                        Print.err(e.getMessage());
                    }
                }
                o.notify();
            }
        });

        pool.execute(() -> {
            synchronized (o) {
                for (char w : words) {
                    System.out.print(w);
                    latch.countDown();
                    t2Start = true;
                    try {
                        o.notify();
                        o.wait();
                    } catch (InterruptedException e) {
                        Print.err(e.getMessage());
                    }
                }
                o.notify();
            }
        });
        pool.shutdown();
    }
}

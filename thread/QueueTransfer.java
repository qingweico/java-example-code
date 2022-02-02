package thread;

import thread.pool.CustomThreadPool;
import util.Constants;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * waiting -> blocking
 * WaitSet -> EntryList
 *
 * @author zqw
 * @date 2021/9/22
 */
class QueueTransfer {
    static final Object O = new Object();
    static CountDownLatch latch = new CountDownLatch(1);
    static Semaphore semaphore = new Semaphore(1);
    static AtomicReference<String> threadName = new AtomicReference<>();
    static ExecutorService pool = CustomThreadPool.newFixedThreadPool(10, 10, 10);
    static volatile boolean flag = false;

    public static void main(String[] args) throws InterruptedException {
        pool.execute(() -> {
            synchronized (O) {
                while (!flag) {
                    latch.countDown();
                    try {
                        O.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.err.println("thread: " + Thread.currentThread().getName() + " run...");
            }
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        TimeUnit.MILLISECONDS.sleep(10);
        synchronized (O) {
            for (int i = 0; i < Constants.FIVE; i++) {
                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // TODO
                pool.execute(() -> {
                    String curThreadName = Thread.currentThread().getName();
                    System.out.println("threadName.get()" + threadName.get());
                    threadName.compareAndSet(threadName.get(), curThreadName);
                    synchronized (O) {
                        System.out.println("thread: " + curThreadName + " run...");
                    }
                });
                semaphore.release();
                System.out.println("thread: " + threadName + " enter entryList...");
            }

            flag = true;
            O.notify();
            // WaitSet -> EntryList
            TimeUnit.MILLISECONDS.sleep(10);
        }
        pool.shutdown();
    }
}

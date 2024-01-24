package thread.lock;

import thread.pool.ThreadObjectPool;
import util.Print;
import util.constants.Constants;

import java.util.concurrent.*;
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
    static CyclicBarrier sync = new CyclicBarrier(2);
    static AtomicReference<String> threadName = new AtomicReference<>();
    static ExecutorService pool = ThreadObjectPool.newFixedThreadPool(10, 10, 10);
    static volatile boolean flag = false;

    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
        pool.execute(() -> {
            synchronized (O) {
                while (!flag) {
                    latch.countDown();
                    try {
                        O.wait();
                    } catch (InterruptedException e) {
                        Print.err(e.getMessage());
                    }
                }
                System.err.println("thread: " + Thread.currentThread().getName() + " run...");
            }
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            Print.err(e.getMessage());
        }
        TimeUnit.MILLISECONDS.sleep(10);
        synchronized (O) {
            for (int i = 0; i < Constants.FIVE; i++) {
                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    Print.err(e.getMessage());
                }

                pool.execute(() -> {
                    String curThreadName = Thread.currentThread().getName();
                    threadName.compareAndSet(threadName.get(), curThreadName);
                    try {
                        sync.await();
                    } catch (InterruptedException | BrokenBarrierException e) {
                        Print.err(e.getMessage());
                    }
                    synchronized (O) {
                        System.out.println("thread: " + curThreadName + " run...");
                    }
                });
                semaphore.release();
                // async -> sync: acquire threadName
                sync.await();
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

package thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

/**
 * @author:qiming
 * @date: 2021/9/22
 */
public class QueueTransfer {

    static final Object lock = new Object();
    static final CountDownLatch latch = new CountDownLatch(1);
    static final Semaphore semaphore = new Semaphore(1);
    static boolean flag = false;

    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (lock) {
                while (!flag) {
                    try {
                        latch.countDown();
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.err.println("thread: " + Thread.currentThread().getName() + " run...");
            }
        }, "t").start();

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized (lock) {


            for (int i = 0; i < 5; i++) {
                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                new Thread(() -> {
                    synchronized (lock) {
                        System.out.println("thread: " + Thread.currentThread().getName() + " run...");
                    }
                }, String.valueOf(i)).start();
                semaphore.release();
                // FIXME
                System.out.println("thread: " + i + " enter entryList...");
            }
            flag = true;
            lock.notify();
        }
    }
}
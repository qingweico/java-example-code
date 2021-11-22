package thread;


import java.util.concurrent.CountDownLatch;

/**
 * @author:qiming
 * @date: 2020/10/23
 * <p>
 * Print ABCDEF 123456 alternately
 */
public class NotifyAndWait {
    // Let the t2 thread run first
    private static Boolean t2Start = false;

    private static final CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) {
        final Object o = new Object();

        char[] numbers = "123456".toCharArray();
        char[] words = "ABCDEF".toCharArray();

        new Thread(() -> {
            try {
                  // The thread on CountDownLatch gets blocked in await()
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (o) {
                while(!t2Start) {
                    try {
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for (char n : numbers) {
                    System.out.print(n);
                    try {
                        // notify    Wakes up a thread in the current waiting queue
                        // notifyAll Wakes up all threads in the current waiting queue
                        o.notify();
                        // Block the current thread to allow the lock, to enter the wait queue
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                o.notify();
            }
        }, "t1").start();

        new Thread(() -> {
            synchronized (o) {
                for (char w : words) {
                    System.out.print(w);
                    latch.countDown();
                    t2Start = true;
                    try {
                        o.notify();
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                o.notify();
            }
        }, "t2").start();
    }
}

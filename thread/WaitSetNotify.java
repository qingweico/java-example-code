package thread;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author:qiming
 * @date: 2021/9/21
 * @see WaitSetSleep
 */
public class WaitSetNotify {
    static final Object lock = new Object();
    static final Semaphore block = new Semaphore(1);
    static boolean hasGoodNetwork = false;

    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (lock) {
                if (!hasGoodNetwork) {
                    try {
                        System.out.println("the network is worse, i will wait 5 second...");
                        // release the lock and the thread enters WAITING state, (ObjectMonitor::WaitSet)
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (!hasGoodNetwork) {
                    System.out.println("I have given up visiting this website!");
                } else {
                    System.out.println("OK! 2s is good!");
                }
            }
        }, "user").start();

        System.out.println("Loading in...");

        try {
            block.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            synchronized (lock) {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                /*
                 * if hasGoodWork = false, then the result is
                 * not good, so wait() and notify() should be
                 * used in conjunction with while, rather than if!
                 */

                hasGoodNetwork = true;
                lock.notify();
                System.out.println("The network will be OK!");
                block.release();
            }
        }, "maintainer").start();

        try {
            block.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                synchronized (lock) {
                    if (hasGoodNetwork) {
                        System.out.println("async rendering...");
                    }
                }
            }, "browser").start();
        }
    }
}

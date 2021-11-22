package thread;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * sleep and lock
 *
 * @author:qiming
 * @date: 2021/9/21
 * @see WaitSetNotify
 */
public class WaitSetSleep {

    static final Object lock = new Object();
    static final Semaphore block = new Semaphore(1);
    static boolean hasGoodNetwork = false;

    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (lock) {
                if (!hasGoodNetwork) {
                    try {
                        System.out.println("the network is worse, i will wait 5 second...");
                        // Sleep do not release the lock, so the other thread enters BLOCKED state,
                        // (ObjectMonitor::EntryList)
                        TimeUnit.SECONDS.sleep(2);
                        block.release();
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (!hasGoodNetwork) {
                    System.out.println("I have given up visiting this website!");
                } else {
                    System.out.println("good!");
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
            System.out.println("wo jin bu lai a!");
            synchronized (lock) {
                hasGoodNetwork = true;
                System.out.println("The network will be OK!");
            }
        }, "maintainer").start();

        try {
            block.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 10; i++) {
            System.out.println("wo ye jin bu lai a!");
            new Thread(() -> {
                synchronized (lock) {
                    if (hasGoodNetwork) {
                        System.out.println("rendering...");
                    }
                }
            }, "browser").start();
        }
    }
}

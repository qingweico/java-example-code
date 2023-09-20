package thread.lock;

import thread.pool.ThreadObjectPool;
import util.constants.Constants;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * wait and unlock
 *
 * @author zqw
 * @date 2021/9/21
 * @see WaitSetSleep
 */
class WaitSetNotify {
    static final Object O = new Object();
    static Semaphore block = new Semaphore(1);
    static boolean hasGoodNetwork = false;
    static ExecutorService pool = ThreadObjectPool.newFixedThreadPool(10, 20, 10);


    public static void main(String[] args) {
        pool.execute(() -> {
            synchronized (O) {
                if (!hasGoodNetwork) {
                    try {
                        System.out.println("the network is worse, i will wait 5 second...");
                        // release the lock and the thread enters WAITING state.
                        // (ObjectMonitor::WaitSet)
                        O.wait();
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
        });

        System.out.println("Loading in...");

        try {
            block.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        pool.execute(() -> {
            synchronized (O) {
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
                O.notify();
                System.out.println("The network will be OK!");
                block.release();
            }
        });

        try {
            block.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < Constants.TEN; i++) {
            pool.execute(() -> {
                synchronized (O) {
                    if (hasGoodNetwork) {
                        System.out.println("async rendering...");
                    }
                }
            });
        }
        pool.shutdown();
    }
}

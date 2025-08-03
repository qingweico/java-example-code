package thread.lock;

import cn.qingweico.concurrent.pool.ThreadObjectPool;
import cn.qingweico.io.Print;
import cn.qingweico.constants.Constants;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * sleep and lock
 *
 * @author zqw
 * @date 2021/9/21
 * @see WaitSetNotify
 */
class WaitSetSleep {

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
                        // Sleep do not release the lock, so the other thread enters BLOCKED state.
                        // (ObjectMonitor::EntryList)
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        Print.err(e.getMessage());
                    }
                }
                if (!hasGoodNetwork) {
                    System.out.println("I have given up visiting this website!");
                } else {
                    System.out.println("good!");
                }
            }
        });

        System.out.println("Loading in...");

        try {
            block.acquire();
        } catch (InterruptedException e) {
            Print.err(e.getMessage());
        }

        pool.execute(() -> {
            synchronized (O) {
                hasGoodNetwork = true;
                System.out.println("The network will be OK!");
                block.release();
            }
        });

        try {
            block.acquire();
        } catch (InterruptedException e) {
            Print.err(e.getMessage());
        }

        for (int i = 0; i < Constants.TEN; i++) {
            pool.execute(() -> {
                synchronized (O) {
                    if (hasGoodNetwork) {
                        System.out.println("rendering...");
                    } else {
                        System.out.println("nothing to do!");
                    }
                }
            });
        }
        pool.shutdown();
    }
}

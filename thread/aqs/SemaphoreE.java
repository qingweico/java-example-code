package thread.aqs;

import thread.pool.CustomThreadPool;
import util.Constants;
import util.Print;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import static util.Print.print;

/**
 * Semaphore: Maintain the current number of threads
 * accessing itself, and provide a synchronization mechanism.
 *
 * @author zqw
 * @date 2020/12/19
 * @see thread.aqs.Semaphore
 */
class SemaphoreE {
    static ExecutorService pool = CustomThreadPool.newFixedThreadPool(10,100,100);

    public static void main(String[] args) {

        Semaphore semaphore = new Semaphore(5);
        for (int i = 0; i < Constants.NUM_100; i++) {
            pool.execute(() -> {
                try {
                    // Obtain permission
                    semaphore.acquire();
                    // Mock execution task!
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    // Release
                    semaphore.release();
                    Print.grace(Thread.currentThread().getName(), "pass!");
                }
            });
        }
        pool.shutdown();
    }
}

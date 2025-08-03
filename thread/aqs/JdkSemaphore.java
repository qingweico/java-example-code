package thread.aqs;

import cn.qingweico.concurrent.pool.ThreadObjectPool;
import cn.qingweico.constants.Constants;
import cn.qingweico.io.Print;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Semaphore: Maintain the current number of threads
 * accessing itself, and provide a synchronization mechanism.
 *
 * @author zqw
 * @date 2020/12/19
 * @see thread.aqs.Semaphore
 */
class JdkSemaphore {
    static ExecutorService pool = ThreadObjectPool.newFixedThreadPool(10,100,100);

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
                    Print.err(e.getMessage());
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

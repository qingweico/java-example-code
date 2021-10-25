package thread;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import static util.Print.print;

/**
 * Semaphore:
 * Maintain the current number of threads accessing itself,
 * and provide a synchronization mechanism.
 *
 * @author:qiming
 * @date: 2020/12/19
 */
public class SemaphoreUsage {
    public static void main(String[] args) {

        Semaphore semaphore = new Semaphore(5);
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
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
                    print(Thread.currentThread().getName() + " pass!");
                }
            }, String.valueOf(i)).start();
        }
    }
}

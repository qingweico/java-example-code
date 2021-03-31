package thread;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import static util.Print.print;

/**
 * semaphore维护当前访问自身的线程个数, 并提供了同步机制
 *
 * @author:qiming
 * @date: 2020/12/19
 */
public class SemaphoreUsage {
    public static void main(String[] args) {

        Semaphore semaphore = new Semaphore(5);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    // Obtain permission
                    semaphore.acquire();
                    print(Thread.currentThread().getName() + "线程-----> 已进入");
                    // Mock execution task!
                    TimeUnit.SECONDS.sleep(5);
                    print(Thread.currentThread().getName() + "线程-----> 已离开");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    // Release
                    semaphore.release();
                }
            }, String.valueOf(i)).start();
        }
    }
}

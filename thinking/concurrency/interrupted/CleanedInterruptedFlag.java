package thinking.concurrency.interrupted;

import thread.pool.ThreadObjectPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author zqw
 * @date 2022/6/26
 */
public class CleanedInterruptedFlag {
    static ExecutorService pool = ThreadObjectPool.newFixedThreadPool(1);

    static Thread t;

    public static void main(String[] args) {
        pool.execute(() -> {
            if (t == null) {
                t = Thread.currentThread();
            }
            while (true) {
                boolean isInterrupted = t.isInterrupted();
                System.out.println("isInterrupted: " + isInterrupted);
                if (isInterrupted) {
                    System.out.println(t.getName() + " is stopped, [isInterrupted]: " + true);
                    break;
                } else {
                    System.out.println(t.getName() + " run...");
                }
                // Thread::sleep
                // The Interrupted state will be cleared resulting in infinite loop.
                try {
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException e) {
                    // Resolving: Again setting the interrupt flag.
                    Thread.currentThread().interrupt();
                    System.err.println(e.getMessage());
                }
            }
        });
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        t.interrupt();
        pool.shutdown();
    }
}

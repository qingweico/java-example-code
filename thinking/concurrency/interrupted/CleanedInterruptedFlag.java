package thinking.concurrency.interrupted;

import lombok.extern.slf4j.Slf4j;
import thread.pool.ThreadObjectPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author zqw
 * @date 2022/6/26
 * @see Interrupting
 */
@Slf4j
public class CleanedInterruptedFlag {
    static ExecutorService pool = ThreadObjectPool.newFixedThreadPool(1);

    static Thread t;

    public static void main(String[] args) throws InterruptedException {
        pool.execute(() -> {
            if (t == null) {
                t = Thread.currentThread();
            }
            while (true) {
                boolean isInterrupted = t.isInterrupted();
                log.info("isInterrupted: {}", isInterrupted);
                if (isInterrupted) {
                    log.info("{} is stopped, [isInterrupted]: {}", t.getName(), true);
                    break;
                } else {
                    log.info("{} run...", t.getName());
                }
                // Thread::sleep
                // The Interrupted state will be cleared resulting in infinite loop.
                try {
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException e) {
                    // Resolving: Again setting the interrupt flag.
                    // InterruptedException 异常被抛出的时机: 当线程在 Thread.sleep
                    // Object.wait Thread.join等方法中阻塞时, 如果线程被中断,
                    // 抛出 InterruptedException, 同时中断状态(中断标志位被置为false)被清除
                    // 注意: 中断状态的清除并不是interrupt()导致的, 而是由 JVM在底层实现的
                    // 1: 其他线程调用了该线程的 interrupt() 方法, JVM 会检测到中断请求
                    // 2: JVM 会唤醒被阻塞的线程
                    // 3: 将线程的中断状态设置为 false
                    // 4: 抛出 InterruptedException
                    Thread.currentThread().interrupt();
                    log.error(e.getMessage());
                }
            }
        });
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            throw new InterruptedException(e.getMessage());
        }
        t.interrupt();
        pool.shutdown();
    }
}

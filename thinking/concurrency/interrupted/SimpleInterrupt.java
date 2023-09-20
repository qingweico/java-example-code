package thinking.concurrency.interrupted;

import lombok.extern.slf4j.Slf4j;
import thread.pool.ThreadObjectPool;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author zqw
 * @date 2022/6/26
 */
@Slf4j
public class SimpleInterrupt {

    static volatile boolean isStop = false;
    static ExecutorService pool = ThreadObjectPool.newFixedThreadPool(1);

    static CountDownLatch latch = new CountDownLatch(1);
    static Thread t;

    public static void main(String[] args) {
        // 使用 volatile 变量
        // 或者使用AtomicBoolean
        pool.execute(() -> {
            while (true) {
                if (t == null) {
                    t = Thread.currentThread();
                }
                if (isStop) {
                    System.out.println(t.getName() + " is stopped, [isStop]: " + isStop);
                    latch.countDown();
                    break;
                } else {
                    System.out.println(t.getName() + " run...");
                }
            }
        });
        try {
            TimeUnit.MILLISECONDS.sleep(1);
            isStop = true;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("使用volatile变量或者AtomicBoolean控制程序中断结束");
        log.info("使用Java中断API程序开始");
        t = null;
        pool.execute(() -> {
           while (true) {
               if (t == null) {
                   t = Thread.currentThread();
               }
               boolean isInterrupted = t.isInterrupted();
               if(isInterrupted) {
                   System.out.println(t.getName() + " is stopped, [isInterrupted]: " + true);
                   break;
               }else {
                   System.out.println(t.getName() + " run...");
               }
           }
        });
        try {
            TimeUnit.MILLISECONDS.sleep(1);
            isStop = true;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        t.interrupt();
        pool.shutdown();
    }
}

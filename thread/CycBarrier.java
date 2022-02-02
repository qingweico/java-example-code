package thread;

import thread.pool.CustomThreadPool;
import util.Constants;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;

/**
 *
 * @author zqw
 * @date 2020/12/19
 * @see thread.aqs.CycBarrier
 */
public class CycBarrier {
    public static void main(String[] args) {
        final ExecutorService pool = CustomThreadPool.newFixedThreadPool(5, 10, 5);

        // It can be used multiple times
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2, () -> System.out.println("main"));
        for (int i = 0; i < Constants.TWO; i++) {
            int finalI = i;
            pool.execute(() -> {
                int count = 10;
                while(count-- > 0) {
                    System.out.println(Thread.currentThread().getName() + ": " + finalI);
                    try {
                        // A thread on the method calling await will block.
                        cyclicBarrier.await();
                    } catch (InterruptedException | BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        pool.shutdown();
    }
}

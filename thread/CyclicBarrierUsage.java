package thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Addition counter
 *
 * @author:qiming
 * @date: 2020/12/19
 */
public class CyclicBarrierUsage {
    public static void main(String[] args) {
        // It can be used multiple times
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2, () -> System.out.println("main"));
        for (int i = 0; i < 2; i++) {
            final int finalI = i;
            new Thread(() -> {
                int count = 10;
                while(count-- > 0) {
                    System.out.println(Thread.currentThread().getName() + " -----> " + finalI);
                    try {
                        // A thread on the method calling await will block
                        cyclicBarrier.await();
                    } catch (InterruptedException | BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            }, "t" + i).start();

        }
    }
}

package thread.aqs;

import cn.qingweico.concurrent.pool.ThreadObjectPool;
import cn.qingweico.constants.Constants;
import cn.qingweico.io.Print;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;

/**
 * @author zqw
 * @date 2020/12/19 {@link CyclicBarrierE}
 */
class CycBarrier {
    public static void main(String[] args) {
        final ExecutorService pool = ThreadObjectPool.newFixedThreadPool(5, 10, 5);

        // It can be used multiple times
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2, () -> System.out.println("main"));
        for (int i = 0; i < Constants.TWO; i++) {
            int finalI = i;
            pool.execute(() -> {
                int count = 10;
                while (count-- > 0) {
                    Print.grace(Thread.currentThread().getName(), finalI);
                    try {
                        // A thread on the method calling await will block.
                        cyclicBarrier.await();
                    } catch (InterruptedException | BrokenBarrierException e) {
                        Print.err(e.getMessage());
                    }
                }
            });
        }
        pool.shutdown();
    }
}

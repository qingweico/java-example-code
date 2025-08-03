package thread.aqs;

import cn.qingweico.concurrent.pool.ThreadObjectPool;
import cn.qingweico.io.Print;
import cn.qingweico.constants.Constants;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;

/**
 * CyclicBarrier
 *
 * @author zqw
 * @date 2021/10/17
 */
class CyclicBarrierE {
    CyclicBarrier barrier;
    final ExecutorService pool = ThreadObjectPool.newFixedThreadPool(2);
    int page = 0;

    public CyclicBarrierE() {
        barrier = new CyclicBarrier(2, () -> {
            System.out.println("sync...");
            page++;
        });
    }

    void prepareProduct() {
        while (page < Constants.TEN) {
            try {
                System.out.println("prepare product...");
                // return the number of thread that waiting in the barrier.
                System.out.println(barrier.await());
            } catch (InterruptedException | BrokenBarrierException e) {
                Print.err(e.getMessage());
            }
        }
    }

    void prepareDeliverOrder() {
        while (page < Constants.TEN) {
            try {
                System.out.println("prepare deliver order...");
                System.out.println(barrier.await());
            } catch (InterruptedException | BrokenBarrierException e) {
                Print.err(e.getMessage());
            }
        }
    }

    void run() {
        pool.execute(this::prepareProduct);
        pool.execute(this::prepareDeliverOrder);
        pool.shutdown();
    }

    public static void main(String[] args) {
        CyclicBarrierE barrier = new CyclicBarrierE();
        barrier.run();
    }
}

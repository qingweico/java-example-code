package thread.aqs;

import thread.pool.CustomThreadPool;
import util.Constants;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;

/**
 * CyclicBarrier
 *
 * @author zqw
 * @date 2021/10/17
 */
public class CyclicBarrierE {
    CyclicBarrier barrier;
    final ExecutorService pool = CustomThreadPool.newFixedThreadPool(2);
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
                e.printStackTrace();
            }
        }
    }

    void prepareDeliverOrder() {
        while (page < Constants.TEN) {
            try {
                System.out.println("prepare deliver order...");
                System.out.println(barrier.await());
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
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

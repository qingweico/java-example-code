package thread.aqs;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author:qiming
 * @date: 2021/10/17
 */
public class CycBarrier {
    CyclicBarrier barrier;
    int page = 0;

    public CycBarrier() {
        barrier = new CyclicBarrier(2, () -> {
            System.out.println("sync...");
            page++;
        });
    }

    void prepareProduct() {
        while (page < 10) {
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
        while (page < 10) {
            try {
                System.out.println("prepare deliver order...");
                System.out.println(barrier.await());
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    void run() {

        new Thread(this::prepareProduct).start();
        new Thread(this::prepareDeliverOrder).start();
    }

    public static void main(String[] args) {
        CycBarrier barrier = new CycBarrier();
        barrier.run();
    }
}

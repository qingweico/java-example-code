package thread.queue;


import thread.pool.ThreadObjectPool;
import util.Print;
import util.constants.Constants;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zqw
 * @date 2021/9/30
 */
class Scheduler {

    /**
     * fair ? Dual Queue : Dual Stack
     */
    static SynchronousQueue<Runnable> synchronousQueue = new SynchronousQueue<>(true);
    /**
     * Dual Queue
     */
    static LinkedTransferQueue<Runnable> transferQueue = new LinkedTransferQueue<>();

    static AtomicInteger idCount = new AtomicInteger(0);

    static ExecutorService pool = ThreadObjectPool.newFixedThreadPool(1000, 1000, 100);


    public Scheduler(int works) {
        for (int i = 0; i < works; i++) {
            pool.execute(new Worker());
        }
    }

    static class Worker implements Runnable {
        int id;

        public Worker() {
            this.id = idCount.getAndIncrement();
        }

        @Override
        public void run() {
            while (true) {
                Runnable runner;
                try {
                    runner = transferQueue.take();
                    runner.run();
                    System.out.format("work done by id=%d\n", id);
                } catch (InterruptedException e) {
                    Print.err(e.getMessage());
                    break;
                }
            }
        }
    }

    public void synchronousSubmit(Runnable r) {
        while (!transferQueue.tryTransfer(r)) {
            Thread.onSpinWait();
            pool.execute(new Worker());
        }
    }

    public void transferSubmit(Runnable r) {
        while (!synchronousQueue.offer(r)) {
            Thread.onSpinWait();
            pool.execute(new Worker());
        }
    }

    public static void main(String[] args) {
        Scheduler scheduler = new Scheduler(10);
        for (int i = 0; i < Constants.NUM_100; i++) {
            scheduler.transferSubmit(() -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    Print.err(e.getMessage());
                }
            });
        }
        pool.shutdown();
    }
}

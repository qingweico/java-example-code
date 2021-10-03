package thread.queue;


import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author:qiming
 * @date: 2021/9/30
 */
public class Scheduler {

    // fair ? Dual Queue : Dual Stack
    static SynchronousQueue<Runnable> synchronousQueue = new SynchronousQueue<>(true);
    // Dual Queue
    static LinkedTransferQueue<Runnable> transferQueue = new LinkedTransferQueue<>();

    static AtomicInteger idCount = new AtomicInteger(0);

    public Scheduler(int works) {
        for (int i = 0; i < works; i++) {
            new Thread(new Worker()).start();
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
                    e.printStackTrace();
                }
            }
        }
    }

    public void synchronousSubmit(Runnable r) {
        while (!transferQueue.tryTransfer(r)) {
            Thread.onSpinWait();
            new Thread(new Worker()).start();
        }
    }

    public void transferSubmit(Runnable r) {
        while (!synchronousQueue.offer(r)) {
            Thread.onSpinWait();
            new Thread(new Worker()).start();
        }
    }

    public static void main(String[] args) {
        Scheduler scheduler = new Scheduler(10);
        for (int i = 0; i < 100; i++) {
            scheduler.transferSubmit(() -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}

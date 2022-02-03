package thread.queue;

import thread.pool.CustomThreadPool;
import util.Constants;

import java.util.concurrent.*;

/**
 * LinkedBlockingQueue
 * LinkedBlockingDeque
 * PriorityBlockingQueue: Not follow FIFO, but according to priority.
 * LinkedBlockingQueue + SynchronousQueue => LinkedTransferQueue: lock-free
 * LinkedTransferQueue: it has a higher performance than LinkedBlockingQueue,
 * and store more element than SynchronousQueue.
 * SynchronousQueue: only store an element, and it will block when try to add the second.
 * DelayQueue
 * {@link thinking.concurrency.juc.DelayQueueUsage}
 * {@link DelayQueueTest}
 *
 * @author zqw
 * @date 2021/9/29
 */
public class BlockingQueueTest {
    static ExecutorService pool = CustomThreadPool.newFixedThreadPool(10, 100, 10);

    public static void main(String[] args) {
        BlockingQueue<Integer> queue;
        // Must init a capacity
        queue = new ArrayBlockingQueue<>(10);


        // Producer
        for (int i = 0; i < Constants.HUNDRED; i++) {
            pool.execute(() -> {
                try {
                    boolean offer = queue.offer((int) (Math.random() * 1000), 3, TimeUnit.SECONDS);
                    System.out.println(offer);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }


        // Consumer
        for (int i = 0; i < Constants.TEN; i++) {
            pool.execute(() -> {
                while (true) {
                    Integer x;
                    try {
                        x = queue.poll(3, TimeUnit.SECONDS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                    System.out.println("Receive: " + x);
                    try {
                        TimeUnit.MILLISECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}

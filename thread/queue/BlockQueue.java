package thread.queue;

import java.util.concurrent.*;

/**
 * @author:qiming
 * @date: 2021/9/29
 */
public class BlockQueue {

    public static void main(String[] args) {
        BlockingQueue<Integer> queue;
        // Must init a capacity
        queue = new ArrayBlockingQueue<>(10);

//        queue = new LinkedBlockingQueue<>(10);
//        queue = new LinkedBlockingDeque<>();
          // Not follow FIFO, but according to priority
//        queue = new PriorityBlockingQueue<>();
          // LinkedBlockingQueue + SynchronousQueue => LinkedTransferQueue lock-free
          // it has a higher performance than LinkedBlockingQueue, and store more element
          // than SynchronousQueue.
//        queue = new LinkedTransferQueue<>();
          // only store a element, and it will block when try to add the second.
//        queue = new SynchronousQueue<>();
          // @see thinking/concurrency/juc/DelayQueueUsage.java
          // @see thread/queue/DelayQ.java
//        queue = new DelayQueue<>();


        // Producer
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                try {
                    boolean offer = queue.offer((int) (Math.random() * 1000), 3, TimeUnit.SECONDS);
                    System.out.println(offer);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }


        // Consumer
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                while (true) {
                    Integer x = null;
                    try {
                        x = queue.poll(3, TimeUnit.SECONDS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Receive: " + x);
                    try {
                        TimeUnit.MILLISECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}

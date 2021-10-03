package thread.queue;

import java.util.concurrent.*;

/**
 * @author:qiming
 * @date: 2021/9/29
 */
public class BlockQueue {

    public static void main(String[] args) {
        BlockingQueue<Integer> queue;
        queue = new ArrayBlockingQueue<>(10);
//        queue = new LinkedBlockingQueue<>(10);
//        queue = new LinkedBlockingDeque<>();
//        queue = new PriorityBlockingQueue<>();
//        queue = new LinkedTransferQueue<>();
//        queue = new SynchronousQueue<>();
//        queue = new DelayQueue<>();


        // Producer
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                try {
                    queue.put((int) (Math.random() * 1000));
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
                        x = queue.take();
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

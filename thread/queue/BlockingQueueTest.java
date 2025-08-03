package thread.queue;

import cn.qingweico.concurrent.pool.ThreadObjectPool;
import cn.qingweico.constants.Constants;
import cn.qingweico.io.Print;
import cn.qingweico.supplier.RandomDataGenerator;

import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ---------------------------- 基于阻塞队列的生产者消费者模型 ----------------------------
 * 基于非阻塞队的请参考 {@link ProducerConsumerModel}
 * {@link ArrayBlockingQueue}
 * {@link LinkedBlockingQueue} 内部基于锁 {@link ReentrantLock} + 条件变量
 * {@link LinkedBlockingDeque}
 * {@link PriorityBlockingQueue}: Not follow FIFO and unbounded, but according to priority.
 * {@code LinkedBlockingQueue} + {@code SynchronousQueue} => LinkedTransferQueue: lock-free
 * {@link LinkedTransferQueue}: it has a higher performance than LinkedBlockingQueue,
 * and store more element than SynchronousQueue.
 * {@link SynchronousQueue}: only store an element, and it will block when try to add the second.
 * SynchronousQueue 在 {@since JDK6} 中利用 CAS 替换掉了原本基于锁的逻辑,同步开销比较小;它是
 * {@link Executors#newCachedThreadPool()} 的默认队列;在队列元素较小的场景下,有着不错的性能表现
 * 并发容器 {@link thread.concurrency.container.Queue}
 *
 * @author zqw
 * @date 2021/9/29
 * @see DelayQueueTest {@link DelayQueue}
 */
public class BlockingQueueTest {
    static ExecutorService pool = ThreadObjectPool.newFixedThreadPool(10, 100, 10);

    public static void main(String[] args) {
        BlockingQueue<Integer> queue;
        // Must init a capacity
        queue = new ArrayBlockingQueue<>(10);


        // Producer
        for (int i = 0; i < Constants.NUM_100; i++) {
            pool.execute(() -> {
                try {
                    // Math.random()可能会返回0的数值(除零异常)且返回值是double,Random.nextInt(bound)从1开始到bound
                    // offer(E) 非阻塞;添加失败返回false
                    // add(E) 非阻塞;添加失败抛异常
                    queue.put(RandomDataGenerator.rndInt(1000));
                } catch (InterruptedException e) {
                    Print.err(e.getMessage());
                }
            });
        }


        // Consumer
        for (int i = 0; i < Constants.TEN; i++) {
            pool.execute(() -> {
                while (true) {
                    Integer x;
                    try {
                        // poll() 非阻塞;添加失败返回false
                        // remove() 非阻塞;添加失败抛异常
                        x = queue.take();
                    } catch (InterruptedException e) {
                        Print.err(e.getMessage());
                        break;
                    }
                    Print.grace("Receive", x);
                    try {
                        TimeUnit.MILLISECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        Print.err(e.getMessage());
                    }
                }
            });
        }
    }
}

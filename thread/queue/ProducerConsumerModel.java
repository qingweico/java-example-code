package thread.queue;


import thread.pool.ThreadObjectPool;
import util.Print;
import util.constants.Constants;
import util.RandomDataUtil;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ---------------------------- 基于非阻塞队列的生产者消费者模型 ----------------------------
 * 锁 + 条件变量;不断轮询和条件判断
 *
 * @author zqw
 * @date 2021/10/16
 */
class ProducerConsumerModel {

    final static int MAX = Constants.TEN;
    final LinkedList<Double> queue = new LinkedList<>();
    ReentrantLock lock = new ReentrantLock();
    /**
     * the sign of queue full
     */
    Condition full = lock.newCondition();
    /**
     * the sign of queue empty
     */
    Condition empty = lock.newCondition();
    static ExecutorService pool = ThreadObjectPool.newFixedThreadPool(10, 100, 10);


    double readData() throws InterruptedException {
        Thread.sleep((RandomDataUtil.ri(10)));
        return RandomDataUtil.ri(10);
    }

    /**
     * Producer
     *
     * @throws InterruptedException An {@code InterruptedException} may be thrown
     */
    void readDb() throws InterruptedException {
        lock.lock();
        try {
            // 队列已满 唤醒消费者消费
            if (queue.size() == MAX) {
                full.signal();
                return;
            }
            var data = readData();
            // 队列为空 唤醒一个生产者生产
            if (queue.size() == 0) {
                empty.signal();
            }
            queue.add(data);
        } finally {
            lock.unlock();
        }
    }


    /**
     * Consumer
     *
     * @throws InterruptedException An {@code InterruptedException} may be thrown
     */
    void calculate() throws InterruptedException {
        lock.lock();
        try {
            // 队列已空 消费者阻塞
            if (queue.size() == 0) {
                full.await();
                return;
            }
            var data = queue.remove();
            // 队列已满 生产者阻塞
            if (queue.size() == MAX) {
                empty.await();
            }
            data *= Math.acos(0.9);
            System.out.println("queue-size: " + queue.size() + ", result: " + String.format("%.2f", data));
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        var p = new ProducerConsumerModel();
        for (int i = 0; i < Constants.NUM_100; i++) {
            pool.execute(() -> {
                while (true) {
                    try {
                        p.readDb();
                    } catch (InterruptedException e) {
                        Print.err(e.getMessage());
                        break;
                    }
                }
            });
        }
        pool.execute(() -> {
            while (true) {
                try {
                    p.calculate();
                } catch (InterruptedException e) {
                    Print.err(e.getMessage());
                    break;
                }
            }
        });
        pool.shutdown();
    }
}

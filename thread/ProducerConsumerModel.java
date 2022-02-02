package thread;


import thread.pool.CustomThreadPool;
import util.Constants;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zqw
 * @date 2021/10/16
 */
class ProducerConsumerModel {

    final static int MAX = Constants.TEN;
    final LinkedList<Double> queue = new LinkedList<>();
    ReentrantLock lock = new ReentrantLock();
    Condition full = lock.newCondition();
    Condition empty = lock.newCondition();
    static ExecutorService pool = CustomThreadPool.newFixedThreadPool(10, 100, 10);


    double readData() throws InterruptedException {
        Thread.sleep((long) (Math.random() * 1000));
        return Math.random() * 10;
    }

    /**
     * Producer
     *
     * @throws InterruptedException An {@code InterruptedException} may be thrown
     */
    void readDb() throws InterruptedException {
        lock.lock();
        try {
            if (queue.size() == MAX) {
                full.await();
                return;
            }
            var data = readData();
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
            if (queue.size() == 0) {
                empty.await();
                return;
            }
            var data = queue.remove();
            if (queue.size() == MAX) {
                full.signal();
            }
            data *= Math.acos(0.9);
            System.out.println("queue-size: " + queue.size() + ", result: " + String.format("%.2f", data));
        } finally {
            lock.unlock();
        }

    }

    public static void main(String[] args) {
        var p = new ProducerConsumerModel();
        for (int i = 0; i < Constants.TEN; i++) {
            pool.execute(() -> {
                while (true) {
                    try {
                        p.readDb();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
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
                    e.printStackTrace();
                    break;
                }
            }
        });
        pool.shutdown();
    }
}

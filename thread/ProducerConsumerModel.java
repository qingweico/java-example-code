package thread;


import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author:qiming
 * @date: 2021/10/16
 */
public class ProducerConsumerModel {

    final static int MAX = 10;
    final LinkedList<Double> queue = new LinkedList<>();
    ReentrantLock lock = new ReentrantLock();
    Condition full = lock.newCondition();
    Condition empty = lock.newCondition();

    double readData() throws InterruptedException {
        Thread.sleep((long) (Math.random()) * 1000);
        return Math.random() * 10;
    }

    // Producer
    void readDb() throws InterruptedException {
        lock.lock();
        if (queue.size() == MAX) {
            full.await();
            return;
        }
        var data = readData();
        if (queue.size() == 0) {
            empty.signal();
        }
        queue.add(data);
        lock.unlock();

    }


    // Consumer
    void calculate() throws InterruptedException {
        lock.lock();
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
        lock.unlock();
    }

    public static void main(String[] args) {
        var p = new ProducerConsumerModel();
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                while (true) {
                    try {
                        p.readDb();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        new Thread(() -> {
            while (true) {
                try {
                    p.calculate();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

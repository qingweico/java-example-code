package thread;

import thread.pool.CustomThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zqw
 * @date 2020/1/03
 */
public class MoviesTicketLock implements Runnable {
    private int ticket = 100;
    private final Lock lock = new ReentrantLock();
    static ExecutorService pool = CustomThreadPool.newFixedThreadPool(3, 3, 1);

    @Override
    public void run() {
        while (true) {
            lock.lock();
            try {
                if (ticket > 0) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "正在出售第" + (ticket--) + "张票");
                } else {
                    return;
                }
            } finally {
                // Release the lock
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        MoviesTicketLock mtl = new MoviesTicketLock();

        pool.execute(mtl);
        pool.execute(mtl);
        pool.execute(mtl);
        pool.shutdown();
    }
}

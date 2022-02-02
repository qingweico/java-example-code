package thread;

import thread.pool.CustomThreadPool;
import util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock WaitSet and EntryList
 *
 * @author zqw
 * @date 2021/6/27
 */
public class LockObjectMonitor {

    static ReentrantLock lock = new ReentrantLock();
    static ExecutorService pool = CustomThreadPool.newFixedThreadPool(10, 10, 5);

    public static void main(String[] args) {
        List<Runnable> taskList = new ArrayList<>();
        for (int i = 0; i < Constants.TEN; i++) {
            taskList.add(() -> {
                // Don't get lock and enter EntryList.
                // The EntryList of the ReentrantLock follows the FIFO.
                System.out.println("\t" + Thread.currentThread().getName());
                lock.lock();
                try {
                    System.out.println("\t" + Thread.currentThread().getName() + "\t");
                    try {
                        TimeUnit.MILLISECONDS.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } finally {
                    lock.unlock();
                }
            });
        }

        lock.lock();
        try {
            System.out.println("Start Sequence:");
            for (Runnable runnable : taskList) {
                pool.execute(runnable);
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            lock.unlock();
            System.out.println("Awake Sequence:");
        }
        pool.shutdown();
    }
}

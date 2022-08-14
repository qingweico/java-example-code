package thread.lock;

import thread.pool.CustomThreadPool;
import util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ######################### Lock 接口 #########################
 * Lock 相对于 synchronized 提供了
 * 1: 非阻塞地获取锁 {@link Lock#tryLock()}
 * 2: 中断地获取锁 {@link thinking.concurrency.interrupted.Interrupting}
 * 3: 超时获取锁 {@link Lock#tryLock()}
 * (对于tryLock() API 当前线程在超时时间内获取了锁;当前线程在超时时间内被中断;超时时间结束都会返回)
 *
 *
 * ReentrantLock WaitSet and EntryList
 *
 * @author zqw
 * @date 2021/6/27
 */
class LockObjectMonitor {

    static ReentrantLock lock = new ReentrantLock();
    static ExecutorService pool = CustomThreadPool.newFixedThreadPool(10, 10, 5);

    public static void main(String[] args) {
        List<Runnable> taskList = new ArrayList<>();
        CustomThreadPool.monitor((ThreadPoolExecutor) pool);
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

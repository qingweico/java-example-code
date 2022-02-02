package thread;

import thread.pool.CustomThreadPool;
import util.Constants;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Reentry lock
 *
 * @author zqw
 * @date 2020/12/18
 */
class ReentrantLockExample {

    ReentrantLock lock = new ReentrantLock();
    static ExecutorService pool = CustomThreadPool.newFixedThreadPool(2, 2, 1);

    public static void main(String[] args) {
        ReentrantLockExample rlu = new ReentrantLockExample();
        pool.execute(rlu::getLock);
        pool.execute(rlu::getLock);
        pool.shutdown();

    }

    public void getLock() {
        try {
            if (lock.tryLock(Constants.TWO, TimeUnit.SECONDS)) {
                System.out.println(Thread.currentThread().getName() + " got the lock!");
                TimeUnit.SECONDS.sleep(9);
            } else {
                System.out.println(Thread.currentThread().getName() + " not got the lock!");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
}

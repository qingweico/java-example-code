package thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Reentry lock
 *
 * @author:qiming
 * @date: 2020/12/18
 */
public class ReentrantLockExample {

    ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        ReentrantLockExample rlu = new ReentrantLockExample();
        new Thread(rlu::getLock, "t1").start();
        new Thread(rlu::getLock, "t2").start();

    }

    public void getLock() {
        try {
            if (lock.tryLock(3, TimeUnit.SECONDS)) {
                System.out.println(Thread.currentThread().getName() + " got the lock!");
                TimeUnit.SECONDS.sleep(9);
            } else {
                System.out.println(Thread.currentThread().getName() + " got the lock!");
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

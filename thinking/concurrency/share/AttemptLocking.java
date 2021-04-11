package thinking.concurrency.share;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import static util.Print.print;
;

/**
 * Locks in the concurrent library allow you to give up on trying to acquire a lock.
 *
 * @author:qiming
 * @date: 2021/1/19
 */
public class AttemptLocking {
    protected ReentrantLock lock = new ReentrantLock();

    public void untimed() {
        boolean captured = lock.tryLock();
        try {
            print("tryLock(): " + captured);
        } finally {
            if (captured) {
                lock.unlock();
            }
        }
    }

    public void timed() {
        boolean captured;
        try {
            captured = lock.tryLock(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            print("tryLock(2, TimeUnit.SECONDS): " + captured);
        } finally {
            if (captured) {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final AttemptLocking al = new AttemptLocking();
        al.untimed();
        al.timed();
        // Now create a separated task to grab the lock:
        new Thread() {
            {
                setDaemon(true);
            }

            @Override
            public void run() {
                al.lock.lock();
                print("acquired");
                try {
                    Thread.sleep(2100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    al.lock.unlock();
                }
            }
        }.start();

        Thread.sleep(100);
        al.untimed();
        al.timed();
    }
}

package thinking.concurrency.share;

import thread.pool.ThreadPoolBuilder;
import util.Print;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Locks in the concurrent library allow you to give up on trying to acquire a lock.
 *
 * @author zqw
 * @date 2021/1/19
 */
public class AttemptLocking {
    protected ReentrantLock lock = new ReentrantLock();

    public void untimed() {
        boolean captured = lock.tryLock();
        try {
            Print.grace("tryLock()", captured);
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
            Print.grace("tryLock(2, TimeUnit.SECONDS)", captured);
        } finally {
            if (captured) {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final AttemptLocking al = new AttemptLocking();
        final ExecutorService single = ThreadPoolBuilder.single(true);
        al.untimed();
        al.timed();
        // Now create a separated task to grab the lock:
        single.execute(() -> {
            Print.println("acquired");
            al.lock.lock();
            try {
                Thread.sleep(2100);
            } catch (InterruptedException e) {
                Print.err(e.getMessage());
            } finally {
                al.lock.unlock();
            }
        });
        single.shutdown();
        Thread.sleep(100);
        al.untimed();
        al.timed();
    }
}

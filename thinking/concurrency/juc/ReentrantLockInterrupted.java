package thinking.concurrency.juc;

import util.Print;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Interrupting a task blocked with a ReentrantLock
 *
 * @author zqw
 * @date 2021/2/7
 */

// Tasks that block on a ReentrantLock task have the ability to be interrupted,
// unlike those that block on synchronized methods or critical sections

class ReentrantLockInterrupted {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new Blocked());
        t.start();
        TimeUnit.SECONDS.sleep(1);
        Print.println("Issuing t.interrupted");
        // Interrupted() can interrupt a call that was blocked by a mutex.
        // Although it is unlikely, t.interrupt() calls can indeed occur before blockedMutex.f().
        t.interrupt();
    }

}

class BlockedMutex {
    private final Lock lock = new ReentrantLock();

    public BlockedMutex() {
        // Acquire it right away, to demonstrate interruption of a
        // task blocked on a ReentrantLock.
        lock.lock();
    }

    @SuppressWarnings("all")
    public void f() {
        try {
            // This will never be available to be second task
            // https://github.com/alibaba/p3c/issues/653
            lock.lockInterruptibly();
            Print.println("lock acquired in f()");
        } catch (InterruptedException e) {
            Print.println("Interrupted from lock acquisition f()");
        }
        finally {
            lock.unlock();
        }
    }
}

class Blocked implements Runnable {
    BlockedMutex blockedMutex = new BlockedMutex();

    @Override
    public void run() {
        Print.println("Waining for f() in BlockedMutex");
        // Blocked
        // The Lock has been taken by the BlockedMutex object, and the lock is not released.
        blockedMutex.f();
        Print.println("Broken out of blocked call");
    }
}

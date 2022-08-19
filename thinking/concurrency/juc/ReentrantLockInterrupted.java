package thinking.concurrency.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static util.Print.print;

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
        print("Issuing t.interrupted");
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

    public void f() {
        try {
            // This will never be available to be second task
            lock.lockInterruptibly();
            print("lock acquired in f()");
        } catch (InterruptedException e) {
            print("Interrupted from lock acquisition f()");
        }
    }
}

class Blocked implements Runnable {
    BlockedMutex blockedMutex = new BlockedMutex();

    @Override
    public void run() {
        print("Waining for f() in BlockedMutex");
        // Blocked
        // The Lock has been taken by the BlockedMutex object, and the lock is not released.
        blockedMutex.f();
        print("Broken out of blocked call");
    }
}

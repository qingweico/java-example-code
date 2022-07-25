package thinking.concurrency.interrupted;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static util.Print.print;

/**
 * Interrupted a task blocked with a ReentrantLock
 *
 * @author zwq
 * @date 2021/4/11
 */
public class InterruptedReentrantLock {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new Blocked0());
        t.start();
        TimeUnit.SECONDS.sleep(1);
        print("Issuing t.interrupted()");
        // Interrupted can interrupt a call that is blocked by a mutex.
        t.interrupt();
    }
}

class BlockMutex {
    private final Lock lock = new ReentrantLock();

    public BlockMutex() {
        // Acquire it right away, to demonstrate interrupted
        // of a task blocked on a ReentrantLock.
        lock.lock();
    }

    public void f() {
        try {
            // This will never be available to a second task
            lock.lockInterruptibly();
        } catch (InterruptedException e) {
            print("Interrupted from lock acquisition in f()");
        }
    }
}

class Blocked0 implements Runnable {
    BlockMutex blocked = new BlockMutex();

    @Override
    public void run() {
        print("Waiting for f() in BlockMutex");
        blocked.f();
        print("Broken out of blocked all");
    }
}
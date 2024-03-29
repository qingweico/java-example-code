package thinking.concurrency.interrupted;

import util.Print;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Interrupted a task blocked with a ReentrantLock
 *
 * @author zqw
 * @date 2021/4/11
 */
class InterruptedReentrantLock {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new Blocked0());
        t.start();
        TimeUnit.SECONDS.sleep(1);
        Print.println("Issuing t.interrupted()");
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

    @SuppressWarnings("all")
    public void f() {
        // lock 获得锁最好放在try语句的第一行 不要放在try语句中 就算放在try语句中也应该放在
        // try语句下面的第一行 这么做都是防止发生异常后 避免锁被无故释放
        // lock 释放锁写在 finally 语句块中
        try {
            // This will never be available to a second task
            // https://github.com/alibaba/p3c/issues/653
            lock.lockInterruptibly();
        } catch (InterruptedException e) {
            Print.println("Interrupted from lock acquisition in f()");
        }
        finally {
            lock.unlock();
        }
    }
}

class Blocked0 implements Runnable {
    BlockMutex blocked = new BlockMutex();

    @Override
    public void run() {
        Print.println("Waiting for f() in BlockMutex");
        blocked.f();
        Print.println("Broken out of blocked all");
    }
}

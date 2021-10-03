package thread.cas;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * @author:qiming
 * @date: 2021/9/26
 */
public class AtomicTryLock {
    AtomicInteger cas = new AtomicInteger(0);

    Thread lockCurrentThread;

    boolean tryLock() {
        System.out.println(Thread.currentThread().getName() + " ----> cas");
        boolean res;
        do {
            res = cas.compareAndSet(0, 1);
            if (res) {
                lockCurrentThread = Thread.currentThread();
            }
        } while (!res);
        return true;
    }

    void unlock() {
        if (lockCurrentThread != Thread.currentThread()) {
            throw new IllegalMonitorStateException();
        }
        cas.compareAndSet(1, 0);
    }

    public static void main(String[] args) {
        AtomicTryLock atomicTryLock = new AtomicTryLock();
        IntStream.range(0, 10).forEach(i -> new Thread(() -> {
            try {
                boolean res = atomicTryLock.tryLock();
                if (res) {
                    atomicTryLock.lockCurrentThread = Thread.currentThread();
                    System.out.println(Thread.currentThread().getName() + " gets the lock");
                } else {
                    System.out.println(Thread.currentThread().getName() + " did not get the lock");
                }
            } finally {
                atomicTryLock.unlock();
            }
        }).start());
    }
}

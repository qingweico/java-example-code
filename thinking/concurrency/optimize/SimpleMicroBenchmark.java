package thinking.concurrency.optimize;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static util.Print.printf;


/**
 * @author:qiming
 * @date: 2021/2/3
 */
public class SimpleMicroBenchmark {
    static long test(Incrementable incr) {
        long start = System.nanoTime();
        for (int i = 0; i < 100_0000L; i++) {
            incr.increment();
        }
        return System.nanoTime() - start;
    }

    public static void main(String[] args) {
        long syncTime = test(new SynchronizingTest());
        long lockTime = test(new LockingTest());
        printf("synchronized: %1$10d\n", syncTime);
        printf("Lock:         %1$10d\n", lockTime);
        printf("Lock/synchronized = %1$.3f",
                (double) lockTime / (double) syncTime);
    }
}

abstract class Incrementable {
    protected long counter = 0;

    public abstract void increment();
}

class SynchronizingTest extends Incrementable {

    @Override
    public synchronized void increment() {
        ++counter;
    }
}

class LockingTest extends Incrementable {
    private final Lock lock = new ReentrantLock();

    @Override
    public void increment() {
        lock.lock();
        try {
            ++counter;
        } finally {
            lock.unlock();
        }
    }
}

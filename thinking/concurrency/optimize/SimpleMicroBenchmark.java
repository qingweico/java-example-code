package thinking.concurrency.optimize;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static cn.qingweico.io.Print.printf;


/**
 * The dangers of microbenchmark
 *
 * @author zqw
 * @date 2021/2/3
 */

class SimpleMicroBenchmark {
    static long test(AbstractIncrementable incr) {
        long start = System.nanoTime();
        long count = 100_0000L;
        for (int i = 0; i < count; i++) {
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

abstract class AbstractIncrementable {
    protected long counter = 0;

    /**
     * increment
     */
    public abstract void increment();
}

class SynchronizingTest extends AbstractIncrementable {

    @Override
    public synchronized void increment() {
        ++counter;
    }
}

class LockingTest extends AbstractIncrementable {
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

package thinking.concurrency.optimize;

import cn.qingweico.concurrent.pool.ThreadObjectPool;
import cn.qingweico.io.Print;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static cn.qingweico.io.Print.printf;

/**
 * Comparing the performance of explicit locks and
 * Atomic versus the synchronized keyword.
 *
 * @author zqw
 * @date 2021/2/3
 */
public class SynchronizationComparisons {
    static BaseLine baseLine = new BaseLine();
    static SynchronizedTest sync = new SynchronizedTest();
    static LockTest lock = new LockTest();
    static AtomicTest atom = new AtomicTest();

    static void test() {
        Print.println("============================");
        printf("%-12s : %13d\n", "Cycles", AbstractAccumulator.cycles);
        baseLine.timeTest();
        sync.timeTest();
        lock.timeTest();
        atom.timeTest();
        AbstractAccumulator.report(sync, baseLine);
        AbstractAccumulator.report(lock, baseLine);
        AbstractAccumulator.report(atom, baseLine);
        AbstractAccumulator.report(sync, baseLine);
        AbstractAccumulator.report(sync, atom);
        AbstractAccumulator.report(lock, atom);
    }

    public static void main(String[] args) {
        // Default
        int iterations = 5;
        // Optionally change iterations
        if (args.length > 0) {
            iterations = Integer.parseInt(args[0]);
        }
        // The First time fills the thread pool:
        Print.println("Warmup");
        baseLine.timeTest();
        // Now the initial test doesn't include the cost of starting the threads
        // for the first time.

        // Producer multiple data points:
        for (int i = 0; i < iterations; i++) {
            test();
            AbstractAccumulator.cycles *= 2;
        }
        AbstractAccumulator.exec.shutdown();
    }
}

abstract class AbstractAccumulator {
    public static long cycles = 5_0000L;
    /**
     * Number of Modifiers and Readers during each test:
     */
    private static final int N = 4;
    public static ExecutorService exec = ThreadObjectPool.newFixedThreadPool(N * 2);
    private final CyclicBarrier barrier = new CyclicBarrier(N * 2 + 1);
    protected volatile int index = 0;
    protected volatile long value = 0;
    protected long duration = 0;
    protected String id = "error";
    protected static final int SIZE = 10_0000;
    protected static int[] preloaded = new int[SIZE];

    static {
        // Load the array of random numbers:
        Random random = new Random(47);
        for (int i = 0; i < SIZE; i++) {
            preloaded[i] = random.nextInt();
        }
    }

    /**
     * accumulate
     */
    public abstract void accumulate();

    /**
     * read
     *
     * @return the read value
     */
    public abstract long read();

    private class Modifier implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < cycles; i++) {
                accumulate();
                try {
                    barrier.await();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private class Reader implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < cycles; i++) {
                value = read();
                try {
                    barrier.await();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void timeTest() {
        long start = System.nanoTime();
        for (int i = 0; i < N; i++) {
            exec.execute(new Modifier());
            exec.execute(new Reader());
        }
        try {
            barrier.await();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        duration = System.nanoTime() - start;
        printf("%-13s: %13d\n", id, duration);
    }

    public static void report(AbstractAccumulator a1, AbstractAccumulator a2) {
        printf("%-22s: %.2f\n", a1.id + "/" + a2.id,
                (double) a1.duration / (double) a2.duration);
    }

}

class BaseLine extends AbstractAccumulator {
    {
        id = "BaseLine";
    }

    @Override
    public void accumulate() {
        value += preloaded[index++];
        if (index >= SIZE) {
            index = 0;
        }
    }

    @Override
    public long read() {
        return value;
    }
}

class SynchronizedTest extends AbstractAccumulator {
    {
        id = "synchronized";
    }

    @Override
    public synchronized void accumulate() {
        value += preloaded[index++];
        if (index >= SIZE) {
            index = 0;
        }
    }

    @Override
    public long read() {
        return value;
    }
}

class LockTest extends AbstractAccumulator {
    {
        id = "Lock";
    }

    private final Lock lock = new ReentrantLock();

    @Override
    public void accumulate() {
        lock.lock();
        try {
            value += preloaded[index++];
            if (index >= SIZE) {
                index = 0;
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public long read() {
        lock.lock();
        try {
            return value;
        } finally {
            lock.unlock();
        }
    }
}

class AtomicTest extends AbstractAccumulator {
    {
        id = "Atomic";
    }

    private final AtomicInteger index = new AtomicInteger(0);
    private final AtomicLong value = new AtomicLong(0);

    @Override
    public void accumulate() {
        // Oops! Relying on or more than one Atomic at a time doesn't work. But it still
        // gives us a performance indicator:
        int i = index.getAndIncrement();
        value.getAndAdd(preloaded[i]);
        if (++i >= SIZE) {
            index.set(0);
        }
    }

    @Override
    public long read() {
        return value.get();
    }
}

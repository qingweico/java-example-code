package thinking.concurrency;

import thread.pool.ThreadObjectPool;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Thread local storage: Automatically giving each thread its own storage.
 * Create different stores for different threads that use the same variable.
 *
 * @author zqw
 * @date 2021/1/31
 */
class ThreadLocalVariableHolder {
    private static final ThreadLocal<Integer> TL = new ThreadLocal<>() {
        private final Random random = new Random(47);

        @Override
        protected synchronized Integer initialValue() {
            return random.nextInt(1000);
        }
    };

    public static void increment() {
        TL.set(TL.get() + 1);
    }

    public static int get() {
        return TL.get();
    }

    public static void main(String[] args) throws InterruptedException {
        int threadCount = 5;
        ExecutorService pool = ThreadObjectPool.newFixedThreadPool(threadCount);
        for (int i = 0; i < threadCount; i++) {
            pool.execute(new Accessor(i));
        }
        // Run for a while
        TimeUnit.SECONDS.sleep(1);

        // All Accessor will quit
        pool.shutdownNow();

    }
}

class Accessor implements Runnable {

    private final int id;

    public Accessor(int idn) {
        id = idn;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            ThreadLocalVariableHolder.increment();
            System.out.println(this);
            Thread.yield();
        }
    }

    @Override
    public String toString() {
        return "#" + id + ": " + ThreadLocalVariableHolder.get();
    }
}

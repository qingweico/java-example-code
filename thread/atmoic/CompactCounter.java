package thread.atmoic;

import thread.pool.CustomThreadPool;
import util.Constants;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;

/**
 * @author zqw
 * @date 2022/7/3
 */
class CompactCounter {
    private volatile long counter;
    private static final AtomicLongFieldUpdater<CompactCounter> UPDATER = AtomicLongFieldUpdater.newUpdater(CompactCounter.class, "counter");

    public synchronized void increase() {
        long old = UPDATER.get(this);
        long newValue = UPDATER.incrementAndGet(this);
        System.out.println(Thread.currentThread().getName() + ": update " + old + " ---> " + newValue);
    }

    public static void main(String[] args) {
        ExecutorService pool = CustomThreadPool.newFixedThreadPool(20, true);
        CompactCounter cc = new CompactCounter();
        for (int i = 0; i < Constants.TWENTY; i++) {
            pool.execute(cc::increase);
        }
        pool.shutdown();
    }
}

package thinking.concurrency;

import jsr166e.extra.AtomicDouble;
import thread.pool.CustomThreadPool;
import util.Constants;

import java.util.concurrent.ExecutorService;

/**
 * @author zqw
 * @date 2021/1/15
 */
class ThreadPriority implements Runnable {

    private int countDown = 5;
    private final AtomicDouble d = new AtomicDouble();
    private final int priority;

    public ThreadPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return Thread.currentThread() + ": " + countDown;
    }

    @Override
    public void run() {
        Thread.currentThread().setPriority(priority);
        while (true) {

            // Use the volatile keyword on the variable d in an effort to ensure that no compiler
            // optimizations are performed.

            // The operation time here is long enough for the thread scheduling mechanism to intervene,
            // it makes the highest-priority thread to be selected first.
            // If you do not add these operations, you will not see the effect of setting the priority.
            for (int i = 0; i < Constants.NUM_100000; i++) {
                d.getAndAdd((Math.PI + Math.E) / (double) i);
                if (i % 1000 == 0) {
                    Thread.yield();
                }
            }
            System.out.println(this);
            if (--countDown == 0) {
                System.out.println("ret: " + d.get());
                return;
            }
        }
    }

    public static void main(String[] args) {
        int threadCount = 5;
        ExecutorService pool = CustomThreadPool.newFixedThreadPool(threadCount,true);
        for (int i = 0; i < threadCount; i++) {
            pool.execute(new ThreadPriority(Thread.MIN_PRIORITY));
            pool.execute(new ThreadPriority(Thread.MAX_PRIORITY));
        }
        pool.shutdown();

        // Although the JDK has 10 priorities, it doesn't map well with most operating
        // systems, so it usually only uses MAX_PRIORITY,NORMAL_PRIORITY, and
        // MAX_PRIORITY.
    }
}

package thinking.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author:qiming
 * @date: 2021/1/15
 */
public class ThreadPriority implements Runnable {

    private int countDown = 5;
    private volatile double d;
    private final int priority;

    public ThreadPriority(int priority) {
        this.priority = priority;
    }

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
            for (int i = 0; i < 100000; i++) {
                d += (Math.PI + Math.E) / (double) i;
                if (i % 1000 == 0) {
                    Thread.yield();
                }
            }
            System.out.println(this);
            if (--countDown == 0) {
                return;
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            exec.execute(new ThreadPriority(Thread.MIN_PRIORITY));
            exec.execute(new ThreadPriority(Thread.MAX_PRIORITY));
        }
        exec.shutdown();

        // Although the JDK has 10 priorities, it doesn't map well with most operating
        // systems, so it usually only uses MAX_PRIORITY,NORMAL_PRIORITY, and
        // MAX_PRIORITY.
    }
}

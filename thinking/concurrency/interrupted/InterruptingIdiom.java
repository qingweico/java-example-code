package thinking.concurrency.interrupted;

import thread.pool.ThreadObjectPool;
import util.Print;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * It is important to clean up resources properly
 * when a program exits the loop via an exception.
 *
 * @author zqw
 * @date 2021/2/7
 */
// General idiom for interrupted a task

class InterruptingIdiom {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService pool = ThreadObjectPool.newFixedThreadPool(1);
        Future<?> submit = pool.submit(new Blocked());
        TimeUnit.MILLISECONDS.sleep(1000);
        submit.cancel(true);
    }
}

class NeedsCleanup {
    private final int id;

    public NeedsCleanup(int id) {
        this.id = id;
        Print.println("NeedsCleanup " + id);
    }

    public void cleanup() {
        Print.println("Cleaning up " + id);
    }
}

class Blocked implements Runnable {
    private double d = 0.0;

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                // point1
                NeedsCleanup nc1 = new NeedsCleanup(1);
                // Start try-finally immediately after definition of nc1, to
                // guarantee proper cleanup of nc1:
                try {
                    Print.println("Sleeping");
                    // Blocking operation:
                    TimeUnit.SECONDS.sleep(1);
                    // point2
                    NeedsCleanup nc2 = new NeedsCleanup(2);
                    // Guarantee proper cleanup of nc2
                    try {
                        Print.println("Calculating");
                        // A time-consuming, non-blocking operation:
                        for (int i = 0; i < 250_0000; i++) {
                            d += (Math.PI + Math.E) / d;
                        }
                        Print.println("Finished time-consuming operation");
                    } finally {
                        nc2.cleanup();
                    }

                } finally {
                    nc1.cleanup();
                }
            }
            // If the interrupted() method is called during a non-blocking operation,
            // the loop exits at the top of the while statement.
            // This sentence will not be printed.
            Print.println("Exiting via while() test");
        } catch (InterruptedException e) {
            // If the interrupted() method is called before or during a blocking
            // operation, the method exits via InterruptedException.
            Print.println("Exiting via InterruptedException");
        }
    }
}

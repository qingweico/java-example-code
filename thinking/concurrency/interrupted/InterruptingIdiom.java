package thinking.concurrency.interrupted;

import java.util.concurrent.TimeUnit;

import static util.Print.print;

/**
 * It is important to clean up resources properly
 * when a program exits the loop via an exception.
 *
 * @author:qiming
 * @date: 2021/2/7
 */
// General idiom for interrupted a task
public class InterruptingIdiom {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new Blocked());
        t.start();
        TimeUnit.MILLISECONDS.sleep(1000);
        t.interrupt();
    }
}

class NeedsCleanup {
    private final int id;

    public NeedsCleanup(int id) {
        this.id = id;
        print("NeedsCleanup " + id);
    }

    public void cleanup() {
        print("Cleaning up " + id);
    }
}

class Blocked implements Runnable {
    private volatile double d = 0.0;

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                // point1
                NeedsCleanup nc1 = new NeedsCleanup(1);
                // Start try-finally immediately after definition of nc1, to
                // guarantee proper cleanup of nc1:
                try {
                    print("Sleeping");
                    // Blocking operation:
                    TimeUnit.SECONDS.sleep(1);
                    // point2
                    NeedsCleanup nc2 = new NeedsCleanup(2);
                    // Guarantee proper cleanup of nc2
                    try {
                        print("Calculating");
                        // A time-consuming, non-blocking operation:
                        for (int i = 0; i < 250_0000; i++) {
                            d += (Math.PI + Math.E) / d;
                        }
                        print("Finished time-consuming operation");
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
            print("Exiting via while() test");
        } catch (InterruptedException e) {
            // If the interrupted() method is called before or during a blocking
            // operation, the method exits via InterruptedException.
            print("Exiting via InterruptedException");
        }
    }
}
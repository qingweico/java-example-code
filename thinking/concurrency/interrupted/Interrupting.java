package thinking.concurrency.interrupted;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static util.Print.exit;
import static util.Print.print;

/**
 * Interrupting a blocked thread
 *
 * @author:qiming
 * @date: 2021/1/31
 */
public class Interrupting {
    private static final ExecutorService exec = Executors.newCachedThreadPool();

    static void test(Runnable r) throws InterruptedException {
        Future<?> task = exec.submit(r);
        TimeUnit.MILLISECONDS.sleep(100);
        print("Interrupting " + r.getClass().getName());
        // Interrupts if running
        task.cancel(true);
        print("Interrupt sent to " + r.getClass().getName());
    }

    public static void main(String[] args) throws InterruptedException {
        test(new SleepBlocked());
        test(new IOBlocked(System.in));
        test(new SynchronizedBlocked());
        TimeUnit.SECONDS.sleep(3);
        print("Aborting with System.exit(0)");
        // Since last 2 interrupts failed
        exit(0);
    }
}
// Interruptible blocking
class SleepBlocked implements Runnable {

    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(100);
        } catch (InterruptedException e) {
            print("InterruptedException");
        }
        print("Exiting SleepBlocked.run()");
    }
}
// Uninterruptible block
class IOBlocked implements Runnable {

    private final InputStream in;

    public IOBlocked(InputStream is) {
        in = is;
    }

    @Override
    public void run() {
        try {
            print("Waiting for read()");
            in.read();
        } catch (IOException e) {
            if (Thread.currentThread().isInterrupted()) {
                print("Interrupted from blocked I/O");
            } else {
                throw new RuntimeException(e);
            }
        }
        print("Exiting IOBlocked.run()");
    }
}
// Uninterruptible block
class SynchronizedBlocked implements Runnable {
    public synchronized void f() {
        // Never releases lock
        while (true) {
            Thread.yield();
        }
    }

    public SynchronizedBlocked() {
        // Lock acquired by this thread
        new Thread(this::f).start();
    }

    @Override
    public void run() {
        print("Trying to call f()");
        f();
        print("Exiting SynchronizedBlocked.run()");

    }
}

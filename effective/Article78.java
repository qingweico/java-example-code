package effective;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import static util.Print.print;

/**
 * 同步访问共享的可变数据
 * The synchronized keyword guarantees that only one thread
 * can execute a method, or a block of code, at any one time.
 *
 * @author zqw
 * @date 2021/1/28
 */
class Article78 {
    public static void main(String[] args) {

    }
}
class NotStopThread {
    // Cooperative thread termination with a volatile field.
    // Or use the volatile keyword to modify stopRequested, Although the volatile
    // modifier does not perform mutually exclusive access, it guarantees that any
    // thread reading the field will see the value that was recently written.

    // private volatile static boolean stopRequested = false;

    private static boolean stopRequested = false;
    private static int i = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread backgroundThread = new Thread(() -> {
            // Because there is no synchronization, there is no guarantee that the background
            // thread will see a change to the stopRequested value made by the main thread.
            while (!stopRequested) {
                i++;
            }
        });
        // The virtual machine makes improvements to the code, which are called hoisting,
        // but it is a liveness failure!
        if(!stopRequested) {
            while (true) {
                i++;
            }
        }
        backgroundThread.start();


        TimeUnit.SECONDS.sleep(1);
        stopRequested = true;
        print(i);
        // The program will not terminate!
    }
}
/** Properly synchronized cooperative thread terminate.*/
class StopThread {
    private static boolean stopRequested = false;

    private static int i = 0;

    public static synchronized void requiredStop() {
        stopRequested = true;
    }

    public static synchronized boolean stopRequested() {
        return stopRequested;
    }

    public static void main(String[] args) throws InterruptedException {
        Thread backgroundThread = new Thread(() -> {
            while (!stopRequested()) {
                i++;
            }
        });
        backgroundThread.start();


        TimeUnit.SECONDS.sleep(1);
        requiredStop();
        print(i);

    }
}

class GenerateSerialNumber {
    private static volatile int nextSerialNumber = 0;

    // Broken! required to be synchronized!
    // because the problem is that the increment operator ++ is not atomic,
    // it performs two operations in the nextSerialNumber field: first it
    // reads the value, and then it writes back a new value equivalent to
    // the original value plus one, so when the second thread reads the value
    // between when the first thread reads the old value and when it writes back
    // the new value, the second thread and the first thread will see the same value,
    // which is a security failure.

    public static int generateSerialNumber() {
        return nextSerialNumber++;
    }

    private static final AtomicLong nextSerialNum = new AtomicLong();

    public static long generateSerialNum() {
        return nextSerialNum.getAndIncrement();
    }
}

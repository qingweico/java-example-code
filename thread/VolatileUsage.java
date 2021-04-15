package thread;

import java.util.concurrent.TimeUnit;

import static util.Print.print;

/**
 * @author:qiming
 * @date: 2020/10/28
 * <p>
 * Three characteristics of threads: visibility, atomicity, orderliness
 * <p>
 * The volatile keyword is a lightweight synchronization mechanism provided
 * by the JVM, visible to the main memory object thread.
 */
public class VolatileUsage {

    public static void main(String[] args) {
        // Access resource class
        MyData myData = new MyData();
        // Create 20 threads, and each thread will do 1000 num increment operations.
        for (int i = 1; i <= 20; i++) {
            new Thread(() -> {
                for (int j = 1; j <= 1000; j++) {
                    myData.addPlusPlus();
                }
            }, String.valueOf(i)).start();
        }

        // Wait until all 20 threads have computed, then use the main thread to get the final result value.
        // Thread.activeCount() > 2 Indicates that the thread is not finished (background default is two
        // threads: main and GC).
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }

        // If the final result is 20,000, it is atomicity, otherwise it is not.
        // Console results: The output is calculated differently each time -- demonstrating that
        // volatile does not guarantee atomicity.
        print(Thread.currentThread().getName() +
                " ----->  The final value of num is : " + myData.num);
    }

}

/**
 * Primary physical memory
 */
class MyData {

    /**
     * Shared Variable
     */
    // The use of the volatile keyword guarantees thread visibility, but not atomicity.
    volatile int num = 0;
    // Using Atomic class could guarantees atomicity
    // AtomicInteger ai = new AtomicInteger();

    /**
     * auto-increment
     */
    public void addPlusPlus() {
        // num++ is not an atomic operation
        num++;
        // ai.getAndIncrement();
    }

}


class VisibilityOfVolatile {
    private static int num = 0;

    // Using the synchronized keyword is also a good option, but these methods are synchronized
    // only for their communication effect, not for mutually exclusive access.
    private static synchronized int getNum() {
        return num;
    }
    private static synchronized void setNum() {
        num = 1;
    }

    // Or using volatile keyword, the t1 thread will see the value of num in the main thread.
    // private static volatile int num = 0;

    public static void main(String[] args) {
        new Thread(() -> {
            // This thread will not end, because t1 thread will save the variable back to main memory after it
            // completes the task and re-read the latest value of the variable in main memory, whereas if it
            // is an empty task, it will not perform the latter.

            // while (num == 0) {}


            // It could end it normally when using synchronized methods with synchronized keyword!
            // while(getNum() == 0) {}

            // Normal ends after one second!
            while (num == 0) {
                // print("Tasks in the t1 thread.");
            }
        }, "t1").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        num = 1;

        // setNum();
        print(num);
    }
}
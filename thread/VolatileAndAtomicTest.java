package thread;


import org.testng.annotations.Test;
import thread.pool.CustomThreadPool;
import util.Constants;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

import static util.Print.print;

/**
 * @author zqw
 * @date 2020/10/28
 * <p>
 * Three characteristics of threads: visibility, atomicity, orderliness
 * <p>
 * The volatile keyword is a lightweight synchronization mechanism provided
 * by the JVM, visible to the main memory object thread.
 * Solved instruction reordering see {@code openjdk: bytecodeinterpreter.cpp;
 * orderaccess_linux_x86.inline.hpp#OrderAccess::fence()}
 */
@SuppressWarnings("unused")
public class VolatileAndAtomicTest {
    static ExecutorService pool = CustomThreadPool.newFixedThreadPool(10, 20, 10);
    static CountDownLatch terminated = new CountDownLatch(Constants.TWENTY);
    private volatile int num = 0;

    /**
     * Using the synchronized keyword is also a good option, but these methods are synchronized
     * only for their communication effect, not for mutually exclusive access.
     *
     * @return the num
     */
    private synchronized int getNum() {
        return num;
    }

    private synchronized void setNum() {
        num = 1;
    }

    // Or using volatile keyword, the t1 thread will see the value of num in the main thread.
    // private static volatile int num = 0;


    @Test
    public void atomicity() {
        // Access resource class
        Resource resource = new Resource();
        // Create 20 threads, and each thread will do 1000 num increment operations.
        for (int i = 1; i <= Constants.TWENTY; i++) {
            pool.execute(() -> {
                for (int j = 1; j <= Constants.THOUSAND; j++) {
                    resource.addPlusPlus();
                }
                terminated.countDown();
            });
        }
        try {
            terminated.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // If the final result is 20,000, it is atomicity, otherwise it is not.
        // Console results: The output is calculated differently each time -- demonstrating that
        // volatile does not guarantee atomicity.
        print(" The final value of num is : " + resource.num);
        print(" The final value of num(atomic) is : " + resource.ai.get());
        pool.shutdown();
    }

    @Test
    public void atomicFieldUpdater() {
        AtomicIntegerFieldUpdater<VolatileAndAtomicTest> fieldUpdater = AtomicIntegerFieldUpdater.
                // Field must be volatile, or throw IllegalArgumentException
                        newUpdater(VolatileAndAtomicTest.class, "num");
        int updatedNum = fieldUpdater.updateAndGet(new VolatileAndAtomicTest(), (x) -> x + 1);
        System.out.println(updatedNum);
    }

    @Test
    public void accumulator() {
        LongAccumulator longAccumulator = new LongAccumulator(Long::sum, 1);
        longAccumulator.accumulate(2);
        System.out.println(longAccumulator.get());

    }

    @Test
    public void adder() {
        LongAdder longAdder = new LongAdder();
        longAdder.add(1);
        longAdder.add(2);
        longAdder.add(3);
        System.out.println(longAdder.sumThenReset());
        System.out.println(longAdder.longValue());
    }

    @Test
    public void visibility() {
        pool.execute(() -> {
            // This thread will not end, because t1 thread will save the variable
            // back to main memory after it completes the task and re-read the
            // latest value of the variable in main memory, whereas if it
            // is an empty task, it will not perform the latter.

            // while (num == 0) {}


            // It could end it normally when using synchronized methods with synchronized keyword!
            // while(getNum() == 0) {}

            // Normal ends after one second.
            while (num == 0) {
                print("Tasks in the t1 thread.");
            }
        });

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        num = 1;

        // setNum();
        print(num);
        pool.shutdown();
    }
}


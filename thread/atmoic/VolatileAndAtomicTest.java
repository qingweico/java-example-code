package thread.atmoic;


import org.testng.annotations.Test;
import thread.pool.ThreadObjectPool;
import util.Print;
import util.constants.Constants;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

/**
 * --------------------- volatile读写不会导致上下文切换 ------------------------
 * volatile 主要是用来保证共享变量的可见性,以防止指令重排序,保证执行的有序性
 * 通过生成class文件之后,反编译文件可以看到被volatile修饰的变量,在写入操作的时候会多一个
 * lock前缀这样的指令,当操作系统执行时会由于这个指令,将当前处理器的数据写回到系统内存中,并
 * 通知其他处理器的缓存失效(MESI);由于内存写操作同时会无效化其他处理器所持有的,指向同一内存
 * 地址的缓存行,因此可以认为其他处理器能够立即见到该 volatile 字段最新值
 * <p>
 * --------------------- Java 内存模型 ------------------------
 * Java 内存模型指 JSR-133 重新定义的 JMM 规范, Java 内存模型是通过
 * 内存屏障来禁止重排序的;对于即时编译器来说,内存屏障将限制它所能做的重
 * 排序优化;对于处理器来说,内存屏障会导致缓存的刷新操作
 * --------------------- volatile ------------------------
 * volatile 字段可以看成一种轻量级,不保证原子性的同步,其性能往往优于
 * (至少不亚于)锁,但是频繁访问volatile字段也会因为不断地强制刷新缓存
 * 而严重影响程序性能;volatile字段地另一个特性是即时编译器无法将其分
 * 配到寄存器中,即volatile字段地每次访问均需要直接从内存中读写
 * --------------------- volatile 对比锁 ---------------------
 * volatile保证了可见性,有序性,而不保证原子性
 * 锁保证原子性,可见性,但不保证有序性
 * 在解锁时,Java虚拟机同样需要刷新缓存,使得当前内存所修改的内存对其他线
 * 程可见但是 synchronized(new Object()) {} 可能等同空操作,而不会
 * 强制刷新缓存
 *
 * @author zqw
 * @date 2020/10/28
 * <p>
 * Three characteristics of threads: visibility, atomicity, orderliness
 * <p>
 * The volatile keyword is a lightweight synchronization mechanism provided
 * by the JVM, visible to the main memory object thread.
 * Solved instruction reordering,
 * see {@code openjdk: bytecodeinterpreter.cpp#OrderAccess::fence();
 * orderaccess_linux_x86.inline.hpp#OrderAccess::fence(); "lock; addl $0,0(%%esp)"}
 */
@SuppressWarnings("unused")
public class VolatileAndAtomicTest {
    static ExecutorService pool = ThreadObjectPool.newFixedThreadPool(10, 20, 10);
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
                for (int j = 1; j <= Constants.NUM_1000; j++) {
                    resource.addPlusPlus();
                }
                terminated.countDown();
            });
        }
        try {
            terminated.await();
        } catch (InterruptedException e) {
            Print.err(e.getMessage());
        }

        // If the final result is 20,000, it is atomicity, otherwise it is not.
        // Console results: The output is calculated differently each time -- demonstrating that
        // volatile does not guarantee atomicity.
        Print.println(" The final value of num is : " + resource.num);
        Print.println(" The final value of num(atomic) is : " + resource.ai.get());
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
                Print.println("Tasks in the t1 thread.");
            }
        });

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            Print.err(e.getMessage());
        }
        num = 1;

        // setNum();
        Print.println(num);
        pool.shutdown();
    }
}


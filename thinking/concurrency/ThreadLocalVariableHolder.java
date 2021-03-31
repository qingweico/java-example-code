package thinking.concurrency;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Thread local storage: Automatically giving each thread its own storage.
 * Create different stores for different threads that use the same variable.
 *
 * @author:周庆伟
 * @date: 2021/1/31
 */
public class ThreadLocalVariableHolder {
    private static final ThreadLocal<Integer> value = new ThreadLocal<>() {
        private final Random random = new Random(47);
        protected synchronized Integer initialValue() {
            return random.nextInt(1000);
        }
    };

    public static void increment() {
        value.set(value.get() + 1);
    }

    public static int get() {return value.get();}

    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        for(int i = 0;i < 5;i++) {
            exec.execute(new Accessor(i));
        }
        // Run for for while
        TimeUnit.SECONDS.sleep(1);

        // All Accessor will quit
        exec.shutdown();

    }
}
class Accessor implements Runnable {

    private final int id;

    public Accessor(int idn) {
        id = idn;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            ThreadLocalVariableHolder.increment();
            System.out.println(this);
            Thread.yield();
        }
    }

    public String toString() {
        return "#" + id + ": " + ThreadLocalVariableHolder.get();
    }
}
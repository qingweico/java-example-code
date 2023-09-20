package jvm;

import thread.pool.ThreadObjectPool;
import util.Print;
import util.constants.Constants;

import java.util.concurrent.ExecutorService;

/**
 * Java 虚拟机类加载机制
 * <p>
 * The JVM ensures that classes are loaded into memory
 * only once in a multithreading environment.
 *
 * @author zqw
 * @date 2021/2/1
 */
class Loading {
    static ExecutorService pool = ThreadObjectPool.newFixedThreadPool(3,
            4, 1);

    public static void main(String[] args) {
        Runnable r = () -> {
            Print.println(Thread.currentThread().getName() + " begin loading...");
            new LoadingClass();
            Print.println(Thread.currentThread().getName() + " end loading...");
        };

        for (int i = 0; i < Constants.TEN; i++) {
            pool.execute(r);
        }
        pool.shutdown();
    }
}

class LoadingClass {
    static {
        System.out.println("The LoadingClass class has " +
                "been initialized by the " +
                Thread.currentThread().getName() + " thread.");
    }
}

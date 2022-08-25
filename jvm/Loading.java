package jvm;

import thread.pool.CustomThreadPool;
import util.Constants;

import java.util.concurrent.ExecutorService;

import static util.Print.print;

/**
 * Java 虚拟机类加载机制
 *
 * The JVM ensures that classes are loaded into memory
 * only once in a multithreading environment.
 *
 * @author zqw
 * @date 2021/2/1
 */
public class Loading {
    static ExecutorService pool = CustomThreadPool.newFixedThreadPool(3,
            4, 1);

    public static void main(String[] args) {
        Runnable r = () -> {
            print(Thread.currentThread().getName() + " begin loading...");
            new LoadingClass();
            print(Thread.currentThread().getName() + " end loading...");
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

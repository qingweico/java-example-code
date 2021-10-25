package jvm;

import static util.Print.print;

/**
 * The JVM ensures that classes are loaded into memory
 * only once in a multithreaded environment.
 *
 * @author:qiming
 * @date: 2021/2/1
 */
public class Loading {
    public static void main(String[] args) {
        Runnable r = () -> {
            print(Thread.currentThread().getName() + " begin loading...");
            LoadingClass lc = new LoadingClass();
            print(Thread.currentThread().getName() + " end loading...");
        };

        new Thread(r, "t1").start();
        new Thread(r, "t2").start();
    }
}

class LoadingClass {
    static {
        System.out.println("The LoadingClass class has " +
                "been initialized by the " +
                Thread.currentThread().getName() + " thread.");
    }
}

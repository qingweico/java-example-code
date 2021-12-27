package design.singleton;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 懒汉式DCL
 * lazy loading
 * @author:qiming
 * @date: 2021/2/3
 */
public class DclSingleton {
    private static long id = 0;

    @Override
    public String toString() {
        return "[ Singleton id = " + id + " ]";
    }

    private DclSingleton() {
        id++;
    }

    // Ensure that instructions around student are not reordered
    private static volatile DclSingleton instance = null;

    // DCL(Double Check Lock)
    public static DclSingleton getInstance() {
        if (instance == null) {
            synchronized (DclSingleton.class) {
                if (instance == null) {
                    instance = new DclSingleton();
                }
            }

        }
        return instance;
    }

    // JDK9
    // Allowing local instructions of a ref to be reordered improves performance.
    private static final AtomicReference<DclSingleton> ref = new AtomicReference<>();

    public static DclSingleton getRef() {
        if (ref.getAcquire() == null) {
            synchronized (DclSingleton.class) {
                if (ref.getAcquire() == null) {
                    ref.setRelease(instance);
                }
            }
        }
        return ref.getAcquire();
    }

    public static void main(String[] args) {
        Thread[] threads = new Thread[100];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                System.out.println(DclSingleton.getInstance());
                System.out.println(DclSingleton.getRef().hashCode());
            });
        }
        Arrays.asList(threads).forEach(Thread::start);
    }
}
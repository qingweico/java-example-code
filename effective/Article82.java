package effective;

import java.util.Collections;
import java.util.*;

/**
 * 线程安全性的同步化
 *
 * @author:qiming
 * @date: 2021/3/24
 */
public class Article82<K, V> {
    public void getMapView() {
        // It is imperative that the user manually synchronized on the returned map when
        // iterating over any of its collective views.
        Map<K, V> m = Collections.synchronizedMap(new HashMap<>());

        // Needn't be in synchronized block
        Set<K> s = m.keySet();
        // Synchronizing on m, not s!
        synchronized (m) {
            for (K key : s) {
                // ...
            }
        }
    }
    // Common accessible lock objects - not recommended!
    // It can be accessed outside of the class and may be subject to access denial
    // attacks (the client only needs to hold the public accessible lock for a timeout).
    // public final Object lock = new Object();


    // Use private lock objects instead of synchronized methods.
    // Private lock object idiom - thwarts denial-of-service attack.
    private final Object lock = new Object();


    public void foo() {
        synchronized (lock) {
        }
    }

    // The lock field is declared final!
    // This prevents the tragic consequences of accidentally changing its contents,
    // which can lead to out-of-sync access to the containing object.
    static class Data {
        public int i = 0;

        // Unsafe!
        private int[] lock = new int[10];

        public void add() {
            synchronized (lock) {
                i++;
            }
            lock = new int[3];
        }
    }

    public static void main(String[] args) {
        Data data = new Data();
        for (int i = 0; i < 40; i++) {
            new Thread(() -> {
                for (int j = 0; j < 50; j++) {
                    data.add();
                }
            }, String.valueOf(i)).start();
        }
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }
        // It may not turn out to be 2000.
        System.out.println(Thread.currentThread().getName() +
                " ----->  The final value of i is : " + data.i);
    }
}




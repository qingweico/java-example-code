package effective;

import thread.pool.CustomThreadPool;

import java.util.Collections;
import java.util.*;
import java.util.concurrent.ExecutorService;

/**
 * 线程安全性的同步化
 *
 * @author zqw
 * @date 2021/3/24
 */
class Article82<K, V> {
    public void getMapView() {
        // It is imperative that the user manually synchronized on the returned map when
        // iterating over any of its collective views.
        Map<K, V> m = Collections.synchronizedMap(new HashMap<>(0));

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
    // It can be accessed outside the class and may be subject to access denial
    // attacks (the client only needs to hold the public accessible lock for a timeout).
    // public final Object lock = new Object();


    /*
     * Use private lock objects instead of synchronized methods.
     * Private lock object idiom - thwarts denial-of-service attack.
     * private final Object lock = new Object();
     */


    /**
     * The lock field is declared final!
     * This prevents the tragic consequences of accidentally changing its contents,
     * which can lead to out-of-sync access to the containing object.
     */
    static class Data {
        public int i = 0;

        // Unsafe!
        private int[] lock = new int[0];

        public void inc() {
            synchronized (lock) {
                i++;
            }
            lock = new int[0];
        }
    }

    public static void main(String[] args) {
        Data data = new Data();
        int threadCount = 40;
        int incCount = 50;
        SlowCountDownLatch latch = new SlowCountDownLatch(threadCount);
        ExecutorService pool = CustomThreadPool.newFixedThreadPool(threadCount);
        for (int i = 0; i < threadCount; i++) {
            pool.execute(() -> {
                for (int j = 0; j < incCount; j++) {
                    data.inc();
                }
                latch.countDown();
            });
        }
        pool.shutdown();
        latch.awaitUnPark();
        // It may not turn out to be 2000.
        System.out.println("The final value of i is : " + data.i);
    }
}




package thread;

import thread.pool.CustomThreadPool;
import util.Constants;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

/**
 * CacheLine(64 byte): the basic unit of CPU sync
 * Cache line isolation has a better efficient that false sharing.
 * MESI
 * @author zqw
 * @date 2022/2/11
 */
public class CacheLinePadding {
    private static final int COUNT = Constants.TEN_MILLION;
    static ExecutorService pool = CustomThreadPool.newFixedThreadPool(2, 2, 2);
    static CountDownLatch latch = new CountDownLatch(2);

    @SuppressWarnings("unused")
    private static class Padding {
        public volatile long p1, p2, p3, p4, p5, p6, p7;
    }

    private static class Tl extends Padding {
        public volatile long x = 0L;
    }

    public static Tl[] arr = new Tl[2];

    static {
        arr[0] = new Tl();
        arr[1] = new Tl();
    }

    public static void main(String[] args) throws Exception {
        pool.execute(() -> {
            for (long i = 0; i < COUNT; i++) {
                arr[0].x = i;
            }
            latch.countDown();
        });

        pool.execute(() -> {
            for (long i = 0; i < COUNT; i++) {
                arr[1].x = i;
            }
            latch.countDown();
        });

        final long start = System.nanoTime();
        latch.await();
        System.out.println((System.nanoTime() - start) / 100_0000);
        pool.shutdown();
    }
}

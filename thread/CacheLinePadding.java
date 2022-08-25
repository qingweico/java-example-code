package thread;

import thread.pool.CustomThreadPool;
import thread.queue.disruptor.DisruptorStarter;
import util.Constants;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

/**
 * CacheLine(64 byte): the basic unit of CPU sync
 * Cache line isolation has a better efficient that false sharing.
 * MESI
 * 缓存行填充 关于缓存行填充的实际使用请看 {@code Disruptor 框架} {@link DisruptorStarter}
 * JDK1.8中 {@code @Contended 注解解决了伪共享问题}
 * | long | long | long | long | long | long | long | x | long | long | long | long | long | long | long |
 * | --------------------- 64 Bytes ------------------|--------------------- 64 Bytes -------------------|
 * @author zqw
 * @date 2022/2/11
 */
class CacheLinePadding {
    private static final int COUNT = Constants.NUM_100000000;
    static ExecutorService pool = CustomThreadPool.newFixedThreadPool(2);
    static CountDownLatch latch = new CountDownLatch(2);

    @SuppressWarnings("unused")
    private static class Padding {
       public long p1, p2, p3, p4, p5, p6, p7;
    }


    private static class Tl extends Padding{
        // private long p1, p2, p3, p4, p5, p6, p7;
        public volatile long x = 0L;
        // private long p9, p10, p11, p12, p13, p14, p15;
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
        System.out.printf("%sms", (System.nanoTime() - start) / 100_0000);
        pool.shutdown();
    }
}

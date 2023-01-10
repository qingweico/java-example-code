package jcip;

import thread.pool.ThreadPoolBuilder;
import util.constants.Constants;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

/**
 * @author zqw
 * @date 2021/3/30
 */
class TestReentrantLockAndAtomic {
    public static void main(String[] args) throws InterruptedException {
        // 180ms
        AtomicPseudoRandom aRand = new AtomicPseudoRandom(20);
        long start = System.currentTimeMillis();
        CountDownLatch allowPass = new CountDownLatch(20);
        int threadCount = 10;
        ExecutorService pool = ThreadPoolBuilder.builder().build();
        for (int i = 0; i < threadCount; i++) {
            pool.execute(() -> {
                for (int j = 0; j < Constants.NUM_1000; j++) {
                    aRand.next(20);
                }
                allowPass.countDown();
            });
        }
        System.out.println(System.currentTimeMillis() - start + "ms");

        // 15ms
        ReentrantLockPseudoRandom rRand = new ReentrantLockPseudoRandom(20);
        start = System.currentTimeMillis();
        for (int i = 0; i < threadCount; i++) {
            pool.execute(() -> {
                for (int j = 0; j < Constants.NUM_1000; j++) {
                    rRand.next(20);
                }
                allowPass.countDown();
            });
        }
        pool.shutdown();
        allowPass.await();
        System.out.println(System.currentTimeMillis() - start + "ms");
    }
}

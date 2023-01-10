package jcip;


import thread.pool.ThreadPoolBuilder;
import util.constants.Constants;
import util.Print;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

/**
 * 使用 synchronized 和 cas 并发累加计数测试
 *
 * @author zqw
 * @date 2021/3/30
 */
class TestSynchronizedAndCas {
    private static final int THREAD_COUNT = 30;
    static ExecutorService pool = ThreadPoolBuilder.builder(THREAD_COUNT).isEnableMonitor(true).build();
    static long acc = 10000;

    private static void exec(Object o, CountDownLatch latch) {
        if (o instanceof CasCounter) {
            CasCounter casCounter = (CasCounter) o;
            for (int i = 0; i < THREAD_COUNT; i++) {
                pool.execute(() -> {
                    for (int j = 0; j < acc; j++) {
                        casCounter.increment();
                    }
                    latch.countDown();
                });
            }
        } else if (o instanceof SynchronizedInteger) {
            SynchronizedInteger integer = (SynchronizedInteger) o;
            for (int i = 0; i < THREAD_COUNT; i++) {
                pool.execute(() -> {
                    for (int j = 0; j < acc; j++) {
                        integer.increment();
                    }
                    latch.countDown();
                });
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch cas = new CountDownLatch(THREAD_COUNT);
        CountDownLatch syn = new CountDownLatch(THREAD_COUNT);
        long start = System.currentTimeMillis();
        SimulatedCas simulatedCas = new SimulatedCas();
        CasCounter casCounter = new CasCounter(simulatedCas);
        exec(casCounter, cas);
        cas.await();
        Print.grace("casCounter", casCounter.getValue());
        Print.grace("cas", (System.currentTimeMillis() - start) + Constants.MS);
        start = System.currentTimeMillis();
        SynchronizedInteger integer = new SynchronizedInteger();
        exec(integer, syn);
        syn.await();
        Print.grace("synCounter", casCounter.getValue());
        Print.grace("syn", (System.currentTimeMillis() - start) + Constants.MS);
        pool.shutdown();
    }
}

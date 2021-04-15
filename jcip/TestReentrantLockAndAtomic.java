package jcip;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author:qiming
 * @date: 2021/3/30
 */
public class TestReentrantLockAndAtomic {
    public static void main(String[] args) {
        AtomicPseudoRandom aRand = new AtomicPseudoRandom(20);
        long start = System.currentTimeMillis();
        ExecutorService exec = Executors.newFixedThreadPool(10);
        for(int i = 0;i < 10;i++) {
            exec.execute(() -> {
                for(int j = 0;j < 1000;j++) {
                    aRand.next(20);
                }
            });
        }
        System.out.println(System.currentTimeMillis() - start + "ms");


        ReentrantLockPseudoRandom rRand = new ReentrantLockPseudoRandom(20);
        start = System.currentTimeMillis();
        for(int i = 0;i < 10;i++) {
            exec.execute(() -> {
                for(int j = 0;j < 1000;j++) {
                    rRand.next(20);
                }
            });
        }
        exec.shutdown();
        System.out.println(System.currentTimeMillis() - start + "ms");
    }
}

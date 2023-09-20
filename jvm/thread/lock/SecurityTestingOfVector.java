package jvm.thread.lock;


import thread.pool.ThreadObjectPool;
import util.constants.Constants;

import java.util.Vector;
import java.util.concurrent.ExecutorService;

/**
 * -XX:+UnlockDiagnosticVMOptions{解锁诊断参数}
 * -XX:+PrintAssembly {打开 JIT 编译后的汇编}
 *
 * @author zqw
 * @date 2021/3/31
 */
public class SecurityTestingOfVector {

    private static final Vector<Integer> V = new Vector<>();


    public static void main(String[] args) {
        ExecutorService pool = ThreadObjectPool.newFixedThreadPool(20, 20, 20);
        int count = 10;
        while (count-- > 0) {
            for (int i = 0; i < Constants.TEN; i++) {
                V.add(i);
            }

            pool.execute(() -> {

                // Synchronization must be added to secure vector access!
                synchronized (V) {
                    for (int i = 0; i < V.size(); i++) {
                        V.remove(i);
                    }
                }
            });

            pool.execute(() -> {
                synchronized (V) {
                    for (Integer integer : V) {
                        System.out.print(integer + " ");
                    }
                }
            });


            // Do not spawn too many threads at the same time, as this will
            // cause the operating system to freeze.
        }
        pool.shutdown();
    }
}

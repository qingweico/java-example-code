package thread.cas;

import thread.pool.ThreadObjectPool;
import util.constants.Constants;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author zqw
 * @date 2021/9/26
 */
class AtomicMarkableReference {

    static final Integer INIT_NUM = Constants.NUM_1000;
    static final Integer UPDATE_NUM = Constants.NUM_100;
    static ExecutorService pool = ThreadObjectPool.newFixedThreadPool(2, 2,1);
    static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(INIT_NUM, 1);

    public static void main(String[] args) {
        Runnable r = () -> {
            Integer value = atomicStampedReference.getReference();
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + " currentValue:" + value + "; version: " + stamp);

            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (atomicStampedReference.compareAndSet(value, UPDATE_NUM, 1, stamp + 1)) {
                System.out.println(Thread.currentThread().getName() + " currentValue:" + atomicStampedReference.getReference() +
                        "; version: " + atomicStampedReference.getStamp());
            } else {
                System.out.println(Thread.currentThread().getName() + " update fail: version different!");
            }
        };
        pool.execute(r);
        pool.execute(r);
        pool.shutdown();
    }
}

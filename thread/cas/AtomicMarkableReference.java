package thread.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author:qiming
 * @date: 2021/9/26
 */
public class AtomicMarkableReference {

    static final Integer INIT_NUM = 1000;
    static final Integer UPDATE_NUM = 100;
    static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(INIT_NUM, 1);

    public static void main(String[] args) {
        new Thread(() -> {
            Integer value = atomicStampedReference.getReference();
            int stamp = atomicStampedReference.getStamp();
            System.out.println("currentValue:" + value + "; version: " + stamp);

            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (atomicStampedReference.compareAndSet(value, UPDATE_NUM, 1, stamp + 1)) {
                System.out.println("currentValue:" + atomicStampedReference.getReference() +
                        "; version: " + atomicStampedReference.getStamp());
            } else {
                System.out.println("update fail: version different!");
            }
        }).start();

    }
}

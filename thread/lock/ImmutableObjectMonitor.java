package thread.lock;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

/**
 * 对象监视器使用不可变的对象(Monitor需要被final修饰并不是Monitor本身是final的)
 * @author zqw
 * @date 2025/7/27
 */
class ImmutableObjectMonitor {

    static Integer count = 0;
    public static void main(String[] args) throws InterruptedException {
        final Object lock = new Object();
        CountDownLatch latch = new CountDownLatch(2);
        CompletableFuture.runAsync(() -> {
            for(int i = 0;i < 10000;i++) {
                // 这里使用 count 作为 Monitor时, 由于自动装箱时会返回一个新的对象
                // 导致 Monitor 引用发生变化, 对象头锁标志位变化, 锁失效
               synchronized (lock) {
                   count++;
               }
            }
            latch.countDown();
        });

        for(int i = 0;i < 10000;i++) {
            synchronized (lock) {
                count++;
            }
        }
        latch.countDown();
        latch.await();
        // 如果使用 count 做为 Monitor, 则结果总是小于20000
        System.out.println(count);
    }
}

package thread.lock;

import cn.qingweico.concurrent.pool.ThreadObjectPool;
import cn.qingweico.io.Print;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * {@link LockSupport#unpark(Thread)}
 * {@link LockSupport#park()}
 * {@link LockSupport#parkNanos(long)} ()}
 *
 * @author zqw
 * @date 2021/9/8
 */
class Park {
    public static void main(String[] args) {

        CountDownLatch latch = new CountDownLatch(1);
        ExecutorService pool = ThreadObjectPool.newFixedThreadPool(2, 2, 1);
        final Thread[] t1 = {null};
        pool.execute(() -> {
            // ensure t1 go first!
            System.out.println("park before t1");
            latch.countDown();
            t1[0] = Thread.currentThread();
            LockSupport.park();
            System.out.println("park after t1");
        });

        pool.execute(() -> {
            try {
                latch.await();
            } catch (InterruptedException e) {
                Print.err(e.getMessage());
            }
            System.out.println("un-park before t2");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                Print.err(e.getMessage());
            }
            LockSupport.unpark(t1[0]);
            System.out.println("un-park after t2");
        });
        pool.shutdown();
        // if there are no using CountDownLatch, may be output
        // un-park before t2
        // park before t1
        // un-park after t2
        // park after t1
    }
}

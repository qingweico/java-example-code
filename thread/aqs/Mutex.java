package thread.aqs;

import thread.pool.CustomThreadPool;
import util.Constants;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * The mutex based on AQS
 *
 * @author zqw
 * @date 2021/10/17
 */
public class Mutex extends AbstractQueuedSynchronizer {
    private static int shared = 0;

    @Override
    protected boolean tryAcquire(int arg) {
        return compareAndSetState(0, 1);
    }

    @Override
    protected boolean tryRelease(int arg) {
        setState(0);
        return true;
    }

    @Override
    protected boolean isHeldExclusively() {
        return getState() == 1;
    }

    public static void main(String[] args) {
        Mutex mutex = new Mutex();
        ExecutorService pool = CustomThreadPool.newFixedThreadPool(10, 10, 1);
        CountDownLatch latch = new CountDownLatch(10);
        for (int i = 0; i < Constants.TEN; i++) {
            pool.execute(() -> {
                mutex.acquire(0);
                for (int j = 0; j < Constants.THOUSAND; j++) {
                    shared++;
                }
                mutex.release(0);
                latch.countDown();
            });
        }
        pool.shutdown();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("shared: " + shared);
    }
}

package thread.aqs;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
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
        ExecutorService exec = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            exec.execute(() -> {
                mutex.acquire(0);
                for (int j = 0; j < 1000; j++) {
                    shared++;
                }
                mutex.release(0);
                latch.countDown();
            });
        }
        exec.shutdown();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("shared: " + shared);
    }
}

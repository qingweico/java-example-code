package thread.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @author:qiming
 * @date: 2021/10/17
 */
public class Semaphore extends AbstractQueuedSynchronizer {

    public Semaphore(int permits) {
        setState(permits);
    }

    @Override
    protected int tryAcquireShared(int arg) {
        var available = getState();
        if (available == 0) {
            return -1;
        }
        var left = available - 1;
        if (compareAndSetState(available, left)) {
            return left;
        }
        return -1;
    }

    @Override
    protected boolean tryReleaseShared(int arg) {
        var available = getState();
        return compareAndSetState(available, available + 1);
    }

    public static void main(String[] args) {

        var semaphore = new Semaphore(5);
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                semaphore.acquireShared(0);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println(Thread.currentThread().getId() + " pass");
                }
                semaphore.releaseShared(0);
            }).start();
        }
    }
}

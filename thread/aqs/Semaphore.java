package thread.aqs;

import thread.pool.CustomThreadPool;
import util.Constants;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @author zqw
 * @date 2021/10/17
 * @see Mutex
 */
public class Semaphore extends AbstractQueuedSynchronizer {
    static CustomThreadPool pool = new CustomThreadPool(5, 5);

    public Semaphore(int permits) {
        setState(permits);
    }

    /*共享式获取同步状态 独占式->Mutex#tryAcquire*/

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
    /*共享式释放同步状态 独占式->Mutex#tryRelease*/

    @Override
    protected boolean tryReleaseShared(int arg) {
        var available = getState();
        return compareAndSetState(available, available + 1);
    }

    public static void main(String[] args) {
        var semaphore = new Semaphore(5);
        for (int i = 0; i < Constants.TWENTY; i++) {
            pool.execute(() -> {
                semaphore.acquireShared(0);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println(Thread.currentThread().getId() + " pass");
                }
                semaphore.releaseShared(0);
            });
        }
        pool.shutdown();
    }
}

package effective;

import thread.pool.CustomThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.LockSupport;

/**
 * 不要依赖于线程调度器
 *
 * @author zqw
 * @date 2021/11/12
 */
class Article84 {
    public static void main(String[] args) {
        SlowCountDownLatch scdl = new SlowCountDownLatch(10);

        int threadCount = 10;
        ExecutorService pool = CustomThreadPool.newFixedThreadPool(threadCount);
        for (int i = 0; i < threadCount; i++) {
            pool.execute(() -> {
                System.out.println(Thread.currentThread().getName());
                scdl.countDown();
            });
        }
        pool.shutdown();
        scdl.awaitUnPark();
        System.out.println("I am last!");
    }
}

class SlowCountDownLatch {
    private int count;
    Thread mainThread = Thread.currentThread();

    public SlowCountDownLatch(int count) {
        if (count < 0) {
            throw new IllegalArgumentException(count + "< 0");
        }
        this.count = count;
    }

    /**
     * Awful CountDownLatch implementation - busy-waits incessantly
     */
    public void await() {
        while (true) {
            synchronized (this) {
                if (count == 0) {
                    return;
                }
            }
        }
    }

    public void awaitUnPark() {
        if (count != 0) {
            LockSupport.park();
        }
    }


    public synchronized void countDown() {
        if (count != 0) {
            count--;
            if (count == 0) {
                LockSupport.unpark(mainThread);
            }
        }
    }
}

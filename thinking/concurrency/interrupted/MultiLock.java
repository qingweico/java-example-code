package thinking.concurrency.interrupted;

import thread.pool.ThreadObjectPool;
import util.Print;

import java.util.concurrent.ExecutorService;

/**
 * One thread can reacquire the same lock
 * @author zqw
 * @date 2021/2/7
 */
class MultiLock {
    public synchronized void f1(int count) {
        if (count-- > 0) {
            Print.println("f1() calling f2() with count " + count);
            f2(count);
        }
    }

    public synchronized void f2(int count) {
        if (count-- > 0) {
            Print.println("f2() calling f1() with count " + count);
            f1(count);
        }
    }

    public static void main(String[] args) {
        final MultiLock multiLock = new MultiLock();
        ExecutorService pool = ThreadObjectPool.newFixedThreadPool(1);
        pool.execute(() -> multiLock.f1(10));
        pool.shutdown();
    }
}

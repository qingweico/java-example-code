package thinking.concurrency.sync;

import thread.pool.ThreadPoolBuilder;
import util.Constants;

import java.util.concurrent.ExecutorService;

import static util.Print.print;

/**
 * Synchronized on another object.
 *
 * @author zqw
 * @date 2021/1/29
 */
class SyncObject {
    public static void main(String[] args) {
        final DualSync ds = new DualSync();
        ExecutorService pool = ThreadPoolBuilder.custom().builder();
        pool.execute(ds::f);
        pool.shutdown();
        ds.g();
    }
}

class DualSync {
    private final Object syncObject = new Object();
    // By synchronizing the entire method, synchronize at this.

    public synchronized void f() {
        for (int i = 0; i < Constants.FIVE; i++) {
            print("f()");
            Thread.yield();
        }
    }

    public void g() {
        // Synchronized blocks on a SyncObject object.
        synchronized (syncObject) {
            for (int i = 0; i < Constants.FIVE; i++) {
                print("g()");
                Thread.yield();
            }
        }
    }
    // These two synchronizations are independent of each other.
}

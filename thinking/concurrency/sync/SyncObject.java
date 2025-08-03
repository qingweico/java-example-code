package thinking.concurrency.sync;

import cn.qingweico.concurrent.pool.ThreadPoolBuilder;
import cn.qingweico.io.Print;
import cn.qingweico.constants.Constants;

import java.util.concurrent.ExecutorService;

/**
 * Synchronized on another object.
 *
 * @author zqw
 * @date 2021/1/29
 */
class SyncObject {
    public static void main(String[] args) {
        final DualSync ds = new DualSync();
        ExecutorService pool = ThreadPoolBuilder.builder().build();
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
            Print.println("f()");
            Thread.yield();
        }
    }

    public void g() {
        // Synchronized blocks on a SyncObject object.
        synchronized (syncObject) {
            for (int i = 0; i < Constants.FIVE; i++) {
                Print.println("g()");
                Thread.yield();
            }
        }
    }
    // These two synchronizations are independent of each other.
}

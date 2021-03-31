package thinking.concurrency.sync;

import static util.Print.print;

/**
 * Synchronized on another object.
 *
 * @author:qiming
 * @date: 2021/1/29
 */
public class SyncObject {
    public static void main(String[] args) {
        final DualSync ds = new DualSync();
        new Thread(ds::f).start();
        ds.g();
    }
}
class DualSync {
    private final Object syncObject = new Object();
    // By synchronizing the entire method, synchronize at this.
    public synchronized void f() {
        for(int i = 0;i < 5;i++){
            print("f()");
            Thread.yield();
        }
    }
    public void g() {
        // Synchronized blocks on a SyncObject object.
        synchronized (syncObject) {
            for(int i = 0;i < 5;i++){
                print("g()");
                Thread.yield();
            }
        }
    }
    // These two synchronizations are independent of each other.
}

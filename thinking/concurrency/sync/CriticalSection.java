package thinking.concurrency.sync;

import thread.pool.CustomThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import static util.Print.exit;
import static util.Print.print;

/**
 * Synchronized blocks instead of entire methods, also demonstrates protection
 * of a non-thread-safe class with a thread-safe one.
 *
 * @author zqw
 * @date 2021/1/29
 */
public class CriticalSection {
    /**Test two different approaches:*/
    static void testApproaches(AbstractPairManager p1, AbstractPairManager p2) {
        ExecutorService exec = CustomThreadPool.newFixedThreadPool(10);
        PairManipulator pm1 = new PairManipulator(p1),
                pm2 = new PairManipulator(p2);
        PairCheck pc1 = new PairCheck(p1),
                pc2 = new PairCheck(p2);
        exec.execute(pm1);
        exec.execute(pm2);
        exec.execute(pc1);
        exec.execute(pc2);
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            print("Sleep interrupted");
        }
        print("pm1: " + pm1 + "\npm2: " + pm2);
        exit(0);
    }

    public static void main(String[] args) {
        AbstractPairManager pm1 = new PairManager1(),
                    pm2 = new PairManager2();
        testApproaches(pm1, pm2);
    }
}
package thinking.concurrency.sync;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Protected a Pair inside a thread-safe class
 *
 * @author zqw
 * @date 2021/1/29
 */

abstract class AbstractPairManager {
    AtomicInteger checkCounter = new AtomicInteger(0);
    protected Pair p = new Pair();
    private final List<Pair> storage = Collections.synchronizedList(new ArrayList<>());

    public synchronized Pair getPair() {
        // Make a copy to keep the original safe.
        return new Pair(p.getX(), p.getY());
    }

    /**
     * Assume this is a time-consuming operation
     */
    protected void store(Pair p) {
        storage.add(p);
        try {
            TimeUnit.MILLISECONDS.sleep(50);
        } catch (InterruptedException ignore) {
            /*ignore*/
        }
    }

    /**
     * increment
     */
    public abstract void increment();

}

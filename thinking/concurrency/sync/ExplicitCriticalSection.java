package thinking.concurrency.sync;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Using explicit lock objects to create critical sections.
 *
 * @author:qiming
 * @date: 2021/1/29
 */
public class ExplicitCriticalSection {
    public static void main(String[] args) {
        PairManager pm1 = new PairManager1(),
                pm2 = new PairManager2();
        CriticalSection.testApproaches(pm1, pm2);
    }
}

/**
 * Synchronized the entire method:
 */
class ExplicitPairManager1 extends PairManager {
    private final Lock lock = new ReentrantLock();

    @Override
    public synchronized void increment() {
        lock.lock();
        try {
            p.incrementX();
            p.incrementY();
            store(getPair());
        } finally {
            lock.unlock();
        }
    }
}

/**
 * Use a critical section:
 */
class ExplicitPairManager2 extends PairManager {
    private final Lock lock = new ReentrantLock();

    @Override
    public void increment() {
        Pair temp;
        lock.lock();
        try {
            p.incrementX();
            p.incrementY();
            temp = getPair();
        } finally {
            lock.unlock();
        }
        store(temp);
    }
}
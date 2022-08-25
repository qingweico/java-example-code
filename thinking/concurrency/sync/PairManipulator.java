package thinking.concurrency.sync;

/**
 * @author zqw
 * @date 2021/1/29
 */
class PairManipulator implements Runnable {
    private final AbstractPairManager pm;

    public PairManipulator(AbstractPairManager pm) {
        this.pm = pm;
    }

    @Override
    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        while (true) {
            pm.increment();
        }
    }

    @Override
    public String toString() {
        return "Pair: " + pm.getPair() + " checkCounter = " + pm.checkCounter.get();
    }
}

class PairCheck implements Runnable {
    private final AbstractPairManager pm;

    public PairCheck(AbstractPairManager pm) {
        this.pm = pm;
    }

    @Override
    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        while (true) {
            pm.checkCounter.incrementAndGet();
            pm.getPair().checkState();
        }
    }
}

// Synchronized the entire method:

class PairManager1 extends AbstractPairManager {

    @Override
    public synchronized void increment() {
        p.incrementX();
        p.incrementY();
        store(getPair());
    }
}

// Use a critical section:

class PairManager2 extends AbstractPairManager {

    @Override
    public void increment() {
        Pair temp;
        synchronized (this) {
            p.incrementX();
            p.incrementY();
            temp = getPair();
        }
        store(temp);
    }
}

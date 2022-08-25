package thinking.concurrency.share;

/**
 * Simplifying mutexes with the synchronized keyword
 * {RunByHand}
 *
 * @author zqw
 * @date 2021/1/19
 */
public class SynchronizedEvenGenerator extends AbstractIntGenerator {
    private int currentEvenValue = 0;
    @Override
    public synchronized int next() {
        ++currentEvenValue;
        // Cause failure faster
        Thread.yield();
        ++currentEvenValue;
        return currentEvenValue;
    }

    public static void main(String[] args) {
        EvenChecker.test(new SynchronizedEvenGenerator());
    }
}

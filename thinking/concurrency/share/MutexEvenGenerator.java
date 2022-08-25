package thinking.concurrency.share;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Use the displayed Lock object
 * While the code is less elegant than synchronized, it is more flexible
 *
 * Preventing thread collisions with mutexes
 * {RunByHand}
 *
 * @author zqw
 * @date 2021/1/19
 */
public class MutexEvenGenerator extends AbstractIntGenerator {
    private int currentEvenValue = 0;
    private final Lock lock = new ReentrantLock();

    @Override
    public int next() {
        lock.lock();
        try {
            ++currentEvenValue;
            Thread.yield();
            ++currentEvenValue;
            // Please attention: The return statement must appear in the try clause to
            // ensure that unlock() does not occur prematurely, thereby exposing the
            // data to the second task.
            return currentEvenValue;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        EvenChecker.test(new MutexEvenGenerator());
    }
}

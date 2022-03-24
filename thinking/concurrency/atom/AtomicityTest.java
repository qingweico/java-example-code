package thinking.concurrency.atom;


import thread.pool.CustomThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import static util.Print.print;


/**
 * @author zqw
 * @date 2021/1/19
 */
public class AtomicityTest implements Runnable {
    private int i = 0;

    /**
     * The lack of synchronization allows the value
     * to be read in an unstable intermediate state.
     */
    public synchronized int getValue() {
        return i;
    }

    private synchronized void evenIncrement() {
        i++;
        i++;
    }

    @Override
    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        while (true) {
            evenIncrement();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec = CustomThreadPool.newFixedThreadPool(1);
        AtomicityTest at = new AtomicityTest();
        exec.execute(at);
        TimeUnit.MICROSECONDS.sleep(100);
        while (true) {
            int value = at.getValue();
            if (value % 2 != 0) {
                print(value);
                System.exit(0);
            }
        }
    }
}

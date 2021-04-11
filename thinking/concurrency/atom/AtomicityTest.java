package thinking.concurrency.atom;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static util.Print.print;


/**
 * @author:qiming
 * @date: 2021/1/19
 */
public class AtomicityTest implements Runnable {
    private int i = 0;

    // TODO
    // The lack of synchronization allows the value
    // to be read in an unstable intermediate state. why?
    public int getValue() {
        return i;
    }

    private synchronized void evenIncrement() {
        i++;
        i++;
    }

    @Override
    public void run() {
        while (true) {
            evenIncrement();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        AtomicityTest at = new AtomicityTest();
        exec.execute(at);
        TimeUnit.MICROSECONDS.sleep(100);
        while (true) {
            int value = at.getValue();
            if (value % 2 != 0) {
                // 102983
                print(value);
                System.exit(0);
            }
        }
    }
}

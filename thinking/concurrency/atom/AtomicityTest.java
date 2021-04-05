package thinking.concurrency.atom;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static util.Print.print;


/**
 * @author:qiming
 * @date: 2021/1/19
 */
public class AtomicityTest implements Runnable {
    private int i = 0;

    public synchronized int getValue() {
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

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        AtomicityTest at = new AtomicityTest();
        exec.execute(at);
        while (true) {
            int value = at.getValue();
            if (value % 2 != 0) {
                print(value);
                System.exit(0);
            }
        }
    }
}

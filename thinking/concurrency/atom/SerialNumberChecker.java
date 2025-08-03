package thinking.concurrency.atom;

import cn.qingweico.concurrent.pool.ThreadObjectPool;
import cn.qingweico.io.Print;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import static cn.qingweico.io.Print.exit;


/**
 * Operations that may seem safe are not when thread is present.
 *
 * @author zqw
 * @date 2021/1/19
 */
public class SerialNumberChecker {
    private static final int SIZE = 100;
    static CircularSet serials = new CircularSet(1000);
    static ExecutorService pool = ThreadObjectPool.newFixedThreadPool(100,true);

    static class SerialChecker implements Runnable {
        @Override
        @SuppressWarnings("InfiniteLoopStatement")
        public void run() {
            while (true) {
                int serial = SerialNumberGenerator.nextSerialNumber();
                if (serials.contains(serial)) {
                    // When using synchronized keywords to modify
                    // methods nextSerialNumber(), this will not be executed.
                    Print.println("Duplicate: " + serial);
                }
                serials.add(serial);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < SIZE; i++) {
            pool.execute(new SerialChecker());
            // Stop after n second if there's an argument
            if (args.length > 0) {
                TimeUnit.SECONDS.sleep(Integer.parseInt(args[0]));
                Print.println("No duplicates detected");
                exit(0);
            }
        }
    }
}

/**
 * Reuses storage so we don't out of memory
 */
class CircularSet {
    private final int[] array;
    private final int len;
    private int index = 0;

    public CircularSet(int size) {
        array = new int[size];
        len = size;
        // Initialize to a value not produced by the SerialNumberGenerator
        Arrays.fill(array, -1);
    }

    public synchronized void add(int i) {
        array[index] = i;
        // Wrap index adds write over an old element
        index = ++index % len;
    }

    public synchronized boolean contains(int val) {
        for (int item : array) {
            if (item == val) {
                return true;
            }
        }
        return false;
    }

}

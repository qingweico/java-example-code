package thinking.concurrency.atom;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static util.Print.*;


/**
 * Operations that may seem safe are not, when thread are present.
 *
 * @author:qiming
 * @date: 2021/1/19
 */
public class SerialNumberChecker {
    private static final int SIZE = 10;
    private static final CircularSet serials = new CircularSet(1000);
    private static final ExecutorService exec = Executors.newCachedThreadPool();

    static class SerialChecker implements Runnable {
        @Override
        public void run() {
            while (true) {
                int serial = SerialNumberGenerator.nextSerialNumber();
                if (serials.contains(serial)) {
                    // When using synchronized keywords to modify
                    // methods nextSerialNumber(), this will not be executed.
                    print("Duplicate: " + serial);
                }
                serials.add(serial);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < SIZE; i++) {
            exec.execute(new SerialChecker());
            // Stop after n second if there's an argument
            if (args.length > 0) {
                TimeUnit.SECONDS.sleep(Integer.parseInt(args[0]));
                print("No duplicates detected");
                exit(0);
            }
        }
    }
}
// Reuses storage so we don't out of memory
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
        // Wrap index add write over old element
        index = ++index % len;
    }

    public synchronized boolean contains(int val) {
        for (int item : array) {
            if (item == val) return true;
        }
        return false;
    }

}

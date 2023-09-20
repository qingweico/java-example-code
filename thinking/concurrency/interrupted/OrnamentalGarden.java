package thinking.concurrency.interrupted;

import thread.pool.ThreadObjectPool;
import util.Print;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author zqw
 * @date 2021/1/31
 */
public class OrnamentalGarden {

    static Integer threadCount = 5;
    public static void main(String[] args) throws InterruptedException {
        ExecutorService pool = ThreadObjectPool.newFixedThreadPool(threadCount);
        for (int i = 0; i < threadCount; i++) {
            pool.execute(new Entrance(i));
        }
        // Run for a while, then stop and collect the data:
        TimeUnit.SECONDS.sleep(3);
        Entrance.cancel();
        pool.shutdown();
        // Wait for each task to finish, and return true if all tasks have finished
        // before the timeout is reached, otherwise return false.
        if (!pool.awaitTermination(250, TimeUnit.MILLISECONDS)) {
            Print.println("Some tasks were not terminated!");
        }
        Print.println("Total: " + Entrance.getTotalCount());
        Print.println("Sum of Entrances: " + Entrance.sumEntrance());
    }
}

class Count {
    private int count = 0;
    private final Random random = new Random(47);

    // Remove the synchronized keyword to see counting fail

    public synchronized int increment() {
        int temp = count;
        // Yield half the time
        if (random.nextBoolean()) {
            Thread.yield();
        }
        return count = ++temp;
    }

    public synchronized int value() {
        return count;
    }
}

class Entrance implements Runnable {

    private static final Count COUNT = new Count();

    private static final List<Entrance> ENTRANCES = new ArrayList<>();

    private int number = 0;

    // Doesn't need synchronization to read:

    private final int id;
    // Atomic operation on a volatile field:

    private static volatile boolean canceled = false;

    public static void cancel() {
        canceled = true;
    }

    public Entrance(int id) {
        this.id = id;
        // Keep this task in a list, Also prevents garbage collection of dead task:
        ENTRANCES.add(this);
    }

    @Override
    public void run() {
        while (!canceled) {
            synchronized (this) {
                ++number;
            }
            Print.println(this + " Total: " + COUNT.increment());
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                Print.println("sleep interrupted");
            }
        }
        Print.println("Stopping " + this);
    }

    public synchronized int getValue() {
        return number;
    }

    @Override
    public String toString() {
        return "Entrance " + id + ": " + getValue();
    }

    public static int getTotalCount() {
        return COUNT.value();
    }

    public static int sumEntrance() {
        int sum = 0;
        for (Entrance entrance : ENTRANCES) {
            sum += entrance.getValue();
        }
        return sum;
    }
}

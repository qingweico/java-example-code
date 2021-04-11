package thinking.concurrency.interrupted;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static util.Print.print;

/**
 * @author:qiming
 * @date: 2021/1/31
 */
public class OrnamentalGarden {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            exec.execute(new Entrance(i));
        }
        // Run for a while, then stop and collect the data:
        TimeUnit.SECONDS.sleep(3);
        Entrance.cancel();
        exec.shutdown();
        // Wait for each task to finish, and return true if all tasks have finished
        // before the timeout is reached, otherwise return false.
        if (!exec.awaitTermination(250, TimeUnit.MILLISECONDS)) {
            print("Some tasks were not terminated!");
        }
        print("Total: " + Entrance.getTotalCount());
        print("Sum of Entrances: " + Entrance.sumEntrance());
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

    private static final Count count = new Count();

    private static final List<Entrance> entrances = new ArrayList<>();

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
        entrances.add(this);
    }

    @Override
    public void run() {
        while (!canceled) {
            synchronized (this) {
                ++number;
            }
            print(this + " Total: " + count.increment());
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                print("sleep interrupted");
            }
        }
        print("Stopping " + this);
    }

    public synchronized int getValue() {
        return number;
    }

    public String toString() {
        return "Entrance " + id + ": " + getValue();
    }

    public static int getTotalCount() {
        return count.value();
    }

    public static int sumEntrance() {
        int sum = 0;
        for (Entrance entrance : entrances) {
            sum += entrance.getValue();
        }
        return sum;
    }
}

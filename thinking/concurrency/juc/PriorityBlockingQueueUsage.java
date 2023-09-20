package thinking.concurrency.juc;

import thread.pool.ThreadObjectPool;
import util.Print;
import util.constants.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

import static util.Print.println;
import static util.Print.print;

/**
 * @author zqw
 * @date 2021/2/2
 */
public class PriorityBlockingQueueUsage {
    public static void main(String[] args) {
        ExecutorService exec = ThreadObjectPool.newFixedThreadPool(2, true);
        PriorityBlockingQueue<Runnable> queue = new PriorityBlockingQueue<>();
        exec.execute(new PrioritizedTaskProducer(queue, exec));
        exec.execute(new PrioritizedTaskConsumer(queue));

    }
}

class PrioritizedTask implements Runnable, Comparable<PrioritizedTask> {
    private final Random random = new Random(47);
    private static int counter = 0;
    private final int id = counter++;
    private final int priority;
    protected List<PrioritizedTask> sequence = new ArrayList<>();

    public PrioritizedTask(int priority) {
        this.priority = priority;
        sequence.add(this);
    }

    @Override
    public void run() {
        try {
            TimeUnit.MILLISECONDS.sleep(random.nextInt(250));
        } catch (InterruptedException e) {
            // Acceptable way to exit
        }
        Print.println(this);
    }

    @Override
    public String toString() {
        return String.format("[%1$-3d]", priority) + " Task " + id;
    }

    public String summary() {
        return "(" + id + ":" + priority + ")";
    }

    public static class EndSentinel extends PrioritizedTask {
        private final ExecutorService e;

        public EndSentinel(ExecutorService e) {
            // Lowest priority in the program
            super(-1);
            this.e = e;
        }

        @Override
        public void run() {
            int count = 0;
            for (PrioritizedTask pt : sequence) {
                print(pt.summary());
                if (++count > 5) {
                    println();
                }
            }
            println();
            Print.println(this + " Calling shutdownNow()");
            e.shutdownNow();
        }
    }

    @Override
    public int compareTo(PrioritizedTask o) {
        return Integer.compare(o.priority, priority);
    }
}

class PrioritizedTaskProducer implements Runnable {
    private final Random random = new Random(47);
    private final Queue<Runnable> queue;
    private final ExecutorService exec;

    public PrioritizedTaskProducer(Queue<Runnable> queue, ExecutorService exec) {
        this.queue = queue;
        // Used for EndSentinel
        this.exec = exec;
    }

    @Override
    public void run() {
        // Unbounded queue; never blocks
        // Fill it up fast with random priorities
        for (int i = 0; i < Constants.TWENTY; i++) {
            queue.add(new PrioritizedTask(random.nextInt(10)));
            Thread.yield();
        }
        // Trickle in highest-priority jobs
        try {
            for (int i = 0; i < Constants.TEN; i++) {
                TimeUnit.MILLISECONDS.sleep(250);
                queue.add(new PrioritizedTask(10));
            }
            for (int i = 0; i < Constants.TEN; i++) {
                queue.add(new PrioritizedTask(i));
                // A sentinel to stop all tasks
                queue.add(new PrioritizedTask.EndSentinel(exec));
            }
        } catch (InterruptedException e) {
            // Acceptable way to exit
        }
        Print.println("Finished PrioritizedTaskProducer");

    }
}

class PrioritizedTaskConsumer implements Runnable {
    private final PriorityBlockingQueue<Runnable> q;

    public PrioritizedTaskConsumer(PriorityBlockingQueue<Runnable> q) {
        this.q = q;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                // Use current thread to run the task
                q.take().run();

            }
        } catch (InterruptedException e) {
            // Acceptable way to exit
        }
        Print.println("Finished PrioritizedTaskConsumer");
    }
}

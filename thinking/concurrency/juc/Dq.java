package thinking.concurrency.juc;

import thread.pool.ThreadObjectPool;
import util.Print;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import static util.Print.println;
import static util.Print.print;

/**
 * DelayQueue
 *
 * @author zqw
 * @date 2021/2/1
 */
class Dq {
    public static void main(String[] args) {
        Random random = new Random(47);
        int taskCount = 20;
        ExecutorService exec = ThreadObjectPool.newFixedThreadPool(3);
        DelayQueue<DelayedTask> queue = new DelayQueue<>();
        // Fill with tasks that have random delays
        for (int i = 0; i < taskCount; i++) {
            queue.put(new DelayedTask(random.nextInt(5000)));
        }
        // Set the stopping point
        queue.add(new DelayedTask.EndSentinel(5000, exec));
        exec.execute(new DelayedTaskConsumer(queue));
    }
}

class DelayedTask implements Runnable, Delayed {
    private static int counter = 0;
    private final int id = counter++;
    private final int delta;
    private final long trigger;
    protected static List<DelayedTask> sequence = new ArrayList<>();

    public DelayedTask(int delayInMilliseconds) {
        delta = delayInMilliseconds;
        trigger = System.nanoTime() + TimeUnit.NANOSECONDS.
                convert(delta, TimeUnit.MILLISECONDS);
        sequence.add(this);
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(trigger -
                System.nanoTime(), TimeUnit.NANOSECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        DelayedTask that = (DelayedTask) o;
        return Long.compare(trigger, that.trigger);
    }

    @Override
    public void run() {
        print(this + " ");
    }

    @Override
    public String toString() {
        return String.format("[%1$-4d]", delta) + " Task " + id;
    }

    public String summary() {
        return "(" + id + ":" + delta + ")";
    }

    public static class EndSentinel extends DelayedTask {
        private final ExecutorService exec;

        public EndSentinel(int delay, ExecutorService e) {
            super(delay);
            exec = e;
        }

        @Override
        public void run() {
            for (DelayedTask dt : sequence) {
                print(dt.summary() + " ");
            }
            println();
            Print.println(this + " Calling shutdownNow()");
            exec.shutdownNow();
        }
    }

}

class DelayedTaskConsumer implements Runnable {
    private final java.util.concurrent.DelayQueue<DelayedTask> q;

    public DelayedTaskConsumer(java.util.concurrent.DelayQueue<DelayedTask> q) {
        this.q = q;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                // Run task with the current thread
                q.take().run();
            }
        } catch (InterruptedException e) {
            // Acceptable way to exit
        }
        Print.println("Finished DelayedTaskConsumer");
    }
}

package thread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @author:qiming
 * @date: 2020/12/20
 */
public class ForkJoinPoolUsage {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Long startTime = System.currentTimeMillis();
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Long> forkJoinTask = new ForkJoinTaskUsage(0L, 1_0000_0000L);
        forkJoinPool.execute(forkJoinTask);
        Long endTime = System.currentTimeMillis();
        long spendTime = endTime - startTime;
        Long result = forkJoinTask.get();
        System.out.println(result + " -----> Time spent " + spendTime / 1000.0 + "s");

    }

}

/**
 * Calculate the sum between one and a hundred million
 */
class ForkJoinTaskUsage extends RecursiveTask<Long> {
    private final long start;
    private final long end;

    public ForkJoinTaskUsage(Long start, Long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long p = 1_0000;
        long sum = 0L;
        if ((end - start) < p) {
            for (long i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        } else {
            long middle = start + ((end - start) >> 1);
            ForkJoinTaskUsage task1 = new ForkJoinTaskUsage(start, middle);
            task1.fork();
            ForkJoinTaskUsage task2 = new ForkJoinTaskUsage(middle, end);
            task2.fork();
            return task1.join() + task2.join();
        }
    }
}
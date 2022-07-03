package thread.pool;

import util.Constants;

import java.util.concurrent.*;

import static util.Print.print;

/**
 * @author zqw
 * @date 2020/12/19
 */
class ThreadPool {
    public static void main(String[] args) throws InterruptedException {

        // On the basis of three Executors tools, but it's not recommended
        // Single threaded thread pool.
         ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();
         singleThreadPool.execute(() -> {});

        // Controllable maximum number of concurrent threads pool.
         ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
        fixedThreadPool.execute(() -> {});
        // The cache thread pool can be reclaimed.
         ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        cachedThreadPool.execute(() -> {});
        // Thread pools that support timed and periodic execution of tasks.
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(3);
        scheduledThreadPool.schedule(() -> {
            // This is executed once after a delay of 3 seconds.
            print("delay 3 second... execute!");
        }, 3, TimeUnit.SECONDS);


        scheduledThreadPool.scheduleAtFixedRate(() -> {
            // Perform every three seconds after a delay of one second.
            print("delay 1 second... every three seconds execute!");
        }, 1, 3, TimeUnit.SECONDS);


        // Good
        ExecutorService executorService = new ThreadPoolExecutor(
                2,
                3,
                1,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(2),
                Executors.defaultThreadFactory(),

                // There are four methods of this strategy:
                // 1.Throw an exception directly.
                // The default policy for thread pools.
                new ThreadPoolExecutor.AbortPolicy()

                // 2.Try to compete with the task at the top of the waiting queue without
                // throwing an exception.
                // new ThreadPoolExecutor.DiscardOldestPolicy().

                // 3.Abort the task without throwing an exception.
                // new ThreadPoolExecutor.DiscardPolicy().

                // 4.Who calls who handles it.
                // new ThreadPoolExecutor.CallerRunsPolicy()
        );
        // If i < 6, there has a RejectedExecutionException will be thrown because maximumPoolSize is 3
        // and the waiting queue's size is 2, when using the default policy for thread pool, drop the task
        // and throw exception if the thread pool queue is full.
        for (int i = 0; i < Constants.FIVE; i++) {
            int finalI = i;
            executorService.execute(() -> System.out.println(Thread.currentThread().getName() + ": " + finalI));
        }

        // The shutdown method performs a gentle process of shutting down: no new tasks are accepted,
        // while tasks that have already been committed are completed, including those that have not
        // yet begun.
        executorService.shutdown();

        // The shutdownNow method performs a brutal shutdown: It tries to cancel all running tasks and
        // no longer starts tasks in the queue that have not yet started.
        executorService.shutdownNow();
    }
}

package thread.pool;

import util.constants.Constants;

import java.util.concurrent.*;

import static util.Print.print;

/**
 * --------------- Executors 中创建线程池的工厂方法 ---------------
 *
 * @author zqw
 * @date 2020/12/19
 * {@link Executors}
 * <p>
 * {@code Executors} 框架的两级调度模型
 * 在 {@code Hotspot VM}的线程模型中Java线程被一对一映射为本地操作系统线程
 * 1: 上层 Java多线程程序通常把应用分解为若干任务 然后使用用户级的调度器将这些任务映射为固定数量的线程
 * 2: 在底层 操作系统内核将这些线程映射到硬件处理器上
 * {@link ScheduledThreadPoolExecutor extends ThreadPoolExecutor} 主要用来在给定的延迟之后运行任务
 * 或定期者执行任务
 *
 * 任务       任务       任务
 *  |          |         |
 *  |          |         |
 *  |          |         |
 *  |          |         |
 *  V          V         V
 *        +++++++++++++
 *        | Executors |
 *        +++++++++++++
 *              |
 *              |
 *              |
 *              |
 *              V
 *             线程
 *              ^
 *              |
 *              |
 *              |
 *              |
 *         +++++++++++++
 *         |  OSKernel  |
 *         +++++++++++++
 *              |
 *              |
 *              |
 *              V
 *             CPU
 *  ################################## Executors 框架的类与接口 ##################################
 *  Runnable Future<V> Callable<V> Executor
 *      ^      ^                       ^
 *      |      |                       |
 *      |      |                       |
 *  RunnableFuture                   ExecutorService
 *        ^                            ^        ^
 *        |                            |        |
 *        |                            |        |
 *    FutureTask                       |        |
 *                                     |        |
 *                AbstractExecutorService      ScheduledExecutorService
 *                             ^                          ^
 *                             |                          |
 *                             |                          |
 *                      ThreadPoolExecutor                |
 *                                      ^                 |
 *                                      |                 |
 *                                      |                 |
 *                                     ScheduledThreadPoolExecutor
 */
class ExecutorsFactoryMethods {
    public static void main(String[] args) throws InterruptedException {

        // On the basis of three Executors tools, but it's not recommended
        // Single threaded thread pool.
        ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();
        singleThreadPool.execute(() -> {
        });

        // Controllable maximum number of concurrent threads pool.
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
        fixedThreadPool.execute(() -> {
        });
        // The cache thread pool can be reclaimed.
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        cachedThreadPool.execute(() -> {
        });
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

        // shutdown 和 shutdownNow 的原理都是遍历线程池中的工作线程 然后逐个调用线程的 interrupted 方法来
        // 中断线程 所以无法响应中断的任务可能无法永远终止

        // shutdownNow 首先将线程池的状态设置为 STOP 然后尝试停止所有的正在执行或者暂停任务的线程 并返回
        // 等待执行任务的列表

        // shutdown 只是将线程池的状态设置为 SHUTDOWN 状态 然后中断所有没有正在执行任务的线程

        // 只要调用上述任何以一个方法都会返回true
        System.out.println(executorService.isShutdown());

        // 而当所有任务都已关闭 才表示线程池关闭成功 返回true
        System.out.println(executorService.isTerminated());
    }
}

package thread.pool;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 自定义线程池参数
 *
 * @author zqw
 * @date 2021/9/29
 */
@Slf4j
public class CustomThreadPool {

    private final BlockingDeque<Runnable> workQueue;
    private static final int DEFAULT_POOL_SIZE = 10;
    private static final int DEFAULT_QUEUE_SIZE = 10;

    private volatile boolean isRun = true;

    public CustomThreadPool(int poolSize, int queueSize) {

        List<WorkThread> workThreads = new ArrayList<>(poolSize);
        for (int i = 0; i < poolSize; i++) {
            workThreads.add(new WorkThread());
        }
        workQueue = new LinkedBlockingDeque<>(queueSize);
        for (WorkThread workThread : workThreads) {
            workThread.start();
        }
    }

    public CustomThreadPool() {
        this(DEFAULT_POOL_SIZE, DEFAULT_QUEUE_SIZE);
    }

    public void shutdown() {
        isRun = false;
    }

    class WorkThread extends Thread {
        @Override
        public void run() {
            while (isRun || workQueue.size() > 0) {
                Runnable run;
                try {
                    run = workQueue.take();
                    run.run();
                } catch (InterruptedException e) {
                    log.error("{}", e.getMessage());
                }
            }
        }
    }

    public void execute(@Nonnull Runnable command) {
        try {
            workQueue.put(command);
        } catch (InterruptedException e) {
            log.error("{}", e.getMessage());
        }
    }

    /*自定义线程池参数start*/

    public static ExecutorService newFixedThreadPool(int corePoolSize, int maxPoolSize, int blockQueueSize,
                                                     boolean preStartAllCore, boolean isEnableMonitor) {
        ThreadPoolExecutor executor = new ThreadPoolExecutorImpl(corePoolSize,
                maxPoolSize,
                60L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(blockQueueSize),
                isEnableMonitor);
        if (preStartAllCore) {
            int coreThreads = executor.prestartAllCoreThreads();
            log.info("{} Core Thread are all started", coreThreads);
        }
        if (isEnableMonitor) {
            log.info("Thread Pool Monitor has enable");
        }
        // 包括核心线程在内,没有任务分配的所有线程,在keepAliveTime时间后全部回收掉
        executor.allowCoreThreadTimeOut(true);
        executor.setRejectedExecutionHandler(new CustomRejectedExecutionHandler());
        executor.setThreadFactory(CustomThreadFactory.guavaThreadFactory());
        return executor;
    }

    public static void buildThreadFactory(ExecutorService executorService, ThreadFactory threadFactory) {
        if (executorService instanceof ThreadPoolExecutor) {
            ThreadPoolExecutor tpe = (ThreadPoolExecutor) executorService;
            tpe.setThreadFactory(threadFactory);
        }
    }

    /**
     * 线程池的监控
     *
     * @param executor {@link ThreadPoolExecutor}
     */
    public static void monitor(ThreadPoolExecutor executor) {
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            // 线程池的线程数量
            log.info("Pool Size: {}", executor.getPoolSize());
            log.info("Number of tasks in Queue: {}", executor.getQueue().size());
            // 获取活动中的线程数
            log.info("Active Threads: {}", executor.getActiveCount());
            // 线程池在运行过程中完成的任务数量
            log.info("Number of tasks completed: {}", executor.getCompletedTaskCount());
            // 线程池需要执行的任务数量
            log.info("Number of tasks total: {}", executor.getTaskCount());
            // 线程池里曾经创建过的最大线程数量;通过这个数量可以知道线程池是否曾经满过
            log.info("Number of largest pool: {}", executor.getLargestPoolSize());
            // 需要 pool 执行 shutdown 或者 shutdownNow 操作触发
            if (executor.isTerminated()) {
                scheduledExecutorService.shutdown();
            }
        }, 0, 1, TimeUnit.SECONDS);
    }
    /*自定义线程池参数end*/


    // ---------------------------- 参数太多 推荐使用构建器模式 {@link ThreadPoolBuilder} ----------------------------
    // ---------------------------- !! Not Recommended ----------------------------

    public static ExecutorService newFixedThreadPool(int corePoolSize, int maxPoolSize, int blockQueueSize, boolean isEnableMonitor) {
        return newFixedThreadPool(corePoolSize, maxPoolSize, blockQueueSize, false, isEnableMonitor);
    }

    public static ExecutorService newFixedThreadPool(int nThreads, boolean isEnableMonitor) {
        return newFixedThreadPool(nThreads, nThreads, nThreads, false, isEnableMonitor);
    }

    public static ExecutorService newFixedThreadPool(int nThreads, boolean preStartAllCore, boolean isEnableMonitor) {
        return newFixedThreadPool(nThreads, nThreads, nThreads, preStartAllCore, isEnableMonitor);
    }

    public static ExecutorService newFixedThreadPool(int corePoolSize, int maxPoolSize, int blockQueueSize) {
        return newFixedThreadPool(corePoolSize, maxPoolSize, blockQueueSize, false, false);
    }

    public static ExecutorService newFixedThreadPool(int nThreads) {
        return newFixedThreadPool(nThreads, nThreads, nThreads, false, false);
    }

    public static ExecutorService newFixedThreadPool(boolean preStartAllCore, int nThreads) {
        return newFixedThreadPool(nThreads, nThreads, nThreads, preStartAllCore, false);
    }
}

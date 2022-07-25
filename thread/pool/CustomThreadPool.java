package thread.pool;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
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

    public static ExecutorService newFixedThreadPool(int corePoolSize, int maxPoolSize, int blockQueueSize,
                                                     boolean preStartAllCore) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize,
                maxPoolSize,
                60L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(blockQueueSize),
                CustomThreadFactory.guavaThreadFactory(),
                new CustomRejectedExecutionHandler()) {
            long start = 0;
            long ret = 0;
            long maxExecutionTime = 0;
            long minExecutionTime = -1;
            double totalExecutionTime = 0.0;
            int totalExecutionCount = 0;

            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                start = System.currentTimeMillis();
            }

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                ret = System.currentTimeMillis() - start;
                totalExecutionTime += ret;
                totalExecutionCount++;
                if (minExecutionTime < ret) {
                    minExecutionTime = ret;
                }
                if (ret > maxExecutionTime) {
                    maxExecutionTime = ret;
                }
            }

            @Override
            protected void terminated() {
                super.terminated();
                log.info("线程池中任务的最大执行时间为: {}ms", maxExecutionTime);
                log.info("线程池中任务的最小执行时间为: {}ms", minExecutionTime);
                log.info("线程池中任务的平均执行时间为: {}ms", totalExecutionTime / totalExecutionCount);
            }
        };
        if (preStartAllCore) {
            int coreThreads = executor.prestartAllCoreThreads();
            log.info("{} Core Thread are all started", coreThreads);
        }
        // 包括核心线程在内,没有任务分配的所有线程,在keepAliveTime时间后全部回收掉
        executor.allowCoreThreadTimeOut(true);
        return executor;
    }

    public static ExecutorService newFixedThreadPool(int corePoolSize, int maxPoolSize, int blockQueueSize) {
        return newFixedThreadPool(corePoolSize, maxPoolSize, blockQueueSize, false);
    }

    public static ExecutorService newFixedThreadPool(int nThreads) {
        return newFixedThreadPool(nThreads, nThreads, nThreads, false);
    }

    public static ExecutorService newFixedThreadPool(int nThreads, boolean preStartAllCore) {
        return newFixedThreadPool(nThreads, nThreads, nThreads, preStartAllCore);
    }

    /**
     * 线程池的监控
     *
     * @param executor {@link ThreadPoolExecutor}
     */
    public static void monitor(ThreadPoolExecutor executor) {
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            log.info("------------------------------");
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
            log.info("------------------------------");
            if (executor.isTerminated()) {
                scheduledExecutorService.shutdown();
            }
        }, 0, 1, TimeUnit.SECONDS);
    }
}

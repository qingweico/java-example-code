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
                    e.printStackTrace();
                }
            }
        }
    }

    public void execute(@Nonnull Runnable command) {
        try {
            workQueue.put(command);
        } catch (InterruptedException e) {
            e.printStackTrace();
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
                new CustomRejectedExecutionHandler());
        if (preStartAllCore) {
            int coreThreads = executor.prestartAllCoreThreads();
            log.info("{} Core Thread are all started", coreThreads);
        }
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

    public static void monitor(ThreadPoolExecutor executor) {
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            log.info("------------------------------");
            log.info("Pool Size: {}", executor.getPoolSize());
            log.info("Number of tasks in Queue: {}", executor.getQueue().size());
            log.info("Active Threads: {}", executor.getActiveCount());
            log.info("Number of tasks completed: {}", executor.getCompletedTaskCount());
            log.info("Number of tasks total: {}", executor.getTaskCount());
            log.info("Number of largest pool: {}", executor.getLargestPoolSize());
            log.info("------------------------------");
            if (executor.isTerminated()) {
                scheduledExecutorService.shutdown();
            }
        }, 0, 1, TimeUnit.SECONDS);
    }
}

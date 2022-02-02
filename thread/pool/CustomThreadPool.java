package thread.pool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author zqw
 * @date 2021/9/29
 */
public class CustomThreadPool {

    private final BlockingDeque<Runnable> workQueue;
    private static final int DEFAULT_MAX_THREAD_SIZE = 10;
    private static final int DEFAULT_DEQUE_SIZE = 10;

    private volatile boolean isRun = true;

    public CustomThreadPool(int maxThreadSize, int dequeSize) {

        List<WorkThread> workThreads = new ArrayList<>(maxThreadSize);
        for (int i = 0; i < maxThreadSize; i++) {
            workThreads.add(new WorkThread());
        }
        workQueue = new LinkedBlockingDeque<>(dequeSize);
        for (WorkThread workThread : workThreads) {
            workThread.start();
        }
    }

    public CustomThreadPool() {
        this(DEFAULT_MAX_THREAD_SIZE, DEFAULT_DEQUE_SIZE);
    }

    public void shutdown() {
        isRun = false;
    }

    class WorkThread extends Thread {
        @Override
        public void run() {
            while (isRun || workQueue.size() > 0) {
                Runnable run = workQueue.poll();
                if (run != null) {
                    run.run();
                }
            }
        }
    }

    public boolean execute(Runnable command) {
        return workQueue.offer(command);
    }

    public static ExecutorService newFixedThreadPool(int corePoolSize, int maxPoolSize, int blockQueueSize) {
        return new ThreadPoolExecutor(corePoolSize,
                maxPoolSize,
                60L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(blockQueueSize),
                CustomThreadFactory.guavaThreadFactory(),
                new CustomRejectedExecutionHandler());
    }
}

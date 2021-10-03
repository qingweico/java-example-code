package thread.pool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author:qiming
 * @date: 2021/9/29
 */
public class CustomThreadPool {

    private BlockingDeque<Runnable> workQueue;

    private boolean isRun = true;

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

    public ExecutorService newFixedThreadPool(int corePoolSize, int maxPoolSize, int blockQueueSize) {
        return new ThreadPoolExecutor(corePoolSize,
                maxPoolSize,
                60L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(blockQueueSize),
                new CustomRejectedExecutionHandler());
    }

    public static void main(String[] args) {
        CustomThreadPool pool = new CustomThreadPool(2, 10);
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            pool.execute(() -> {
                System.out.println(Thread.currentThread().getName() + " :" + finalI);
            });
        }
        pool.shutdown();
    }
}

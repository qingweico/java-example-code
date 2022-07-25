package thread.pool;

import util.Constants;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * --------------- 线程池任务拒绝策略 ---------------
 *
 * @author zqw
 * @date 2021/9/29
 * 抛出异常;默认策略{@link ThreadPoolExecutor.AbortPolicy}
 * 由提交任务的线程执行 {@link ThreadPoolExecutor.CallerRunsPolicy}
 * 丢弃队列最前面的任务,然后重新提交被拒绝的任务{@link ThreadPoolExecutor.DiscardOldestPolicy}
 * 丢弃任务,但是不抛异常{@link ThreadPoolExecutor.DiscardPolicy}
 */
public class CustomRejectedExecutionHandler implements RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        System.out.println("task rejected!");
    }

    public static void main(String[] args) {
        ExecutorService executorService = CustomThreadPool.newFixedThreadPool(2, 2, 5);
        for (int i = 0; i < Constants.TEN; i++) {
            int finalI = i;
            executorService.execute(() -> System.out.println(Thread.currentThread().getName() + ": " + finalI));
        }
        executorService.shutdown();

    }
}

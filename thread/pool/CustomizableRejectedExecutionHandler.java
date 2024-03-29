package thread.pool;

import lombok.extern.slf4j.Slf4j;
import thread.ThreadUtils;
import util.constants.Constants;

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
@Slf4j
public class CustomizableRejectedExecutionHandler implements RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        // main thread execute
        if (!executor.isShutdown()) {
            r.run();
        }
        // just tip
        log.info("Thread Pool Rejected Task, Main Thread Execute...");

    }

    public static void main(String[] args) {
        ExecutorService executorService = ThreadObjectPool.newFixedThreadPool(2, 4, 5);
        for (int i = 0; i < Constants.TEN; i++) {
            int finalI = i;
            executorService.execute(() -> ThreadUtils.gracePrint(String.valueOf(finalI)));
        }
        executorService.shutdown();

    }
}

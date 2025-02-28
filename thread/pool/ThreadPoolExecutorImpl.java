package thread.pool;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author zqw
 * @date 2022/8/19
 */
@Slf4j
public class ThreadPoolExecutorImpl extends ThreadPoolExecutor {
    public ThreadPoolExecutorImpl(int corePoolSize,
                                  int maximumPoolSize,
                                  long keepAliveTime,
                                  TimeUnit unit,
                                  BlockingQueue<Runnable> workQueue,
                                  boolean isEnableMonitor) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        this.isEnableMonitor = isEnableMonitor;
    }

    private final ThreadLocal<Long> startTimeTl = ThreadLocal.withInitial(() -> 0L);
    private final ThreadLocal<BigDecimal> thisTimeCostTl = ThreadLocal.withInitial(() -> BigDecimal.ZERO);
    private final AtomicReference<BigDecimal> maxExecutionTime = new AtomicReference<>(BigDecimal.valueOf(-1));
    private final AtomicReference<BigDecimal> minExecutionTime = new AtomicReference<>(BigDecimal.valueOf(Double.MAX_VALUE));
    private final AtomicReference<BigDecimal> totalExecutionTime = new AtomicReference<>(BigDecimal.ZERO);
    private final AtomicInteger totalExecutionCount = new AtomicInteger(0);
    boolean isEnableMonitor;

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        if (isEnableMonitor) {
            startTimeTl.set(System.currentTimeMillis());
        }
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        if (isEnableMonitor) {
            long start = startTimeTl.get();
            BigDecimal thisTimeCost = BigDecimal.valueOf(System.currentTimeMillis() - start);
            totalExecutionTime.updateAndGet(currentTime -> currentTime.add(thisTimeCost));
            maxExecutionTime.updateAndGet(current -> current.compareTo(thisTimeCost) < 0 ? thisTimeCost : current);
            minExecutionTime.updateAndGet(current -> current.compareTo(thisTimeCost) > 0 ? thisTimeCost : current);
            totalExecutionCount.incrementAndGet();
            thisTimeCostTl.set(thisTimeCost);
            // remove tl
            startTimeTl.remove();
            thisTimeCostTl.remove();
        }
    }

    @Override
    protected void terminated() {
        super.terminated();
        if (isEnableMonitor) {
            ThreadObjectPool.monitor(this);
            BigDecimal averageExecutionTime = totalExecutionTime.get().divide(BigDecimal.valueOf(totalExecutionCount.get()), 2, RoundingMode.HALF_UP);
            log.info("线程池中任务的最大执行时间为: {}ms", maxExecutionTime.get());
            log.info("线程池中任务的最小执行时间为: {}ms", minExecutionTime.get());
            log.info("线程池中任务的平均执行时间为: {}ms", averageExecutionTime);
        }
    }
}

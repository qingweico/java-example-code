package effective;

import cn.hutool.core.thread.ThreadUtil;
import cn.qingweico.concurrent.pool.ThreadPoolBuilder;
import cn.qingweico.convert.TimeUnitConverter;
import cn.qingweico.supplier.RandomDataGenerator;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Executor、task、流优于直接使用线程
 *
 * @author zqw
 * @date 2025/12/9
 * @see ExecutorCompletionService 按任务完成顺序获取结果, 对比 {@link ExecutorService#invokeAll(Collection)}
 * 按完成顺序处理结果,避免快任务被慢任务阻塞
 * @see Executors#newCachedThreadPool() 适合轻量负载的服务器使用(大量短期异步任务)
 * @see Executors#newFixedThreadPool(int) 适合大负载的服务器使用
 * @see ForkJoinPool
 * @see ForkJoinTask
 * @see Stream#parallel()
 */
@Slf4j
public class Article80 {
    // 避免直接使用线程 线程既是工作单元又是执行机制
    // 而 executor 中工作单元和执行机制是分开的
    // 将工作单元抽象为任务,即Task(Runnable和Callable,后者相对前者有返回值且可以抛出异常)
    public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {
        ExecutorService executor = ThreadPoolBuilder.create();
        List<Callable<RemoteCall>> tasks = new ArrayList<>();
        IntStream.range(0, 10).forEach(e -> tasks.add(new RemoteCall(RandomDataGenerator.rndInt(5000 + e))));
        // 等待所有任务完成(合理设置超时时间,避免长时间阻塞)
        // 关键特性: invokeAll 返回时, 所有Future都是已完成状态, 即isDone()都是true
        // 但是超时未完成的任务isCancelled() 是 true, 因为invokeAll超时自动调用cancel()
        // Future 状态变为已取消
        List<Future<RemoteCall>> invokeAll = executor.invokeAll(tasks, 3, TimeUnit.SECONDS);
        ExecutorCompletionService<RemoteCall> ecs = new ExecutorCompletionService<>(executor);
        List<Object> invokeAllRet = new ArrayList<>();
        for (Future<RemoteCall> future : invokeAll) {
            if (future.isCancelled()) {
                invokeAllRet.add("任务超时取消");
            } else {
                try {
                    // 不会阻塞, invokeAll 返回的都是有结果的Future
                    // 已取消的 Future 调用 get() 一定会抛出 CancellationException
                    RemoteCall rc = future.get();
                    invokeAllRet.add(rc);
                } catch (ExecutionException e) {
                    // 任务执行过程中抛出异常
                    invokeAllRet.add("任务执行异常");
                } catch (CancellationException e) {
                    // 任务被取消
                    // 需要双重检查, future.isCancelled() 与 future.get()
                    // 非原子操作, 可能被其他线程取消
                    invokeAllRet.add("任务超时取消");
                } catch (InterruptedException e) {
                    // 等待过程中线程被中断
                    Thread.currentThread().interrupt();
                    invokeAllRet.add("任务中断");
                }
            }
        }
        log.info("invokeAll 返回的结果: {}", invokeAllRet);
        CompletableFuture<List<RemoteCall>> ecsRet = submitAllAndCollect(ecs, tasks);
        ecsRet.thenAccept(results -> log.info("ExecutorCompletionService 返回的结果: {}", results))
                .join();
        tasks.clear();
        IntStream.range(0, 5).forEach(e ->
                tasks.add(new RemoteCall(
                        RandomDataGenerator.rndInt(10000 + e))));
        // 返回第一个成功的结果(只要有一个任务成功就返回,取执行最快的那个任务,取消其他未完成任务)
        // 只有任务全部失败才会抛出异常
        Object invokeAny = executor.invokeAny(tasks);
        log.info("invokeAny 返回的结果: {}", invokeAny);
        executor.shutdown();

    }

    static class RemoteCall implements Callable<RemoteCall> {
        private final long milliseconds;

        public RemoteCall(long milliseconds) {
            this.milliseconds = milliseconds;
        }

        @Override
        public RemoteCall call() {
            ThreadUtil.sleep(milliseconds);
            log.info("执行耗时 : {}", TimeUnitConverter.convertMills(milliseconds));
            return this;
        }

        @Override
        public String toString() {
            return String.valueOf(milliseconds);
        }
    }

    private static CompletableFuture<List<RemoteCall>> submitAllAndCollect(ExecutorCompletionService<RemoteCall> ecs, List<Callable<RemoteCall>> tasks) {

        // 提交所有任务
        tasks.forEach(ecs::submit);

        // 创建 CompletableFuture 来收集结果
        CompletableFuture<List<RemoteCall>> resultFuture = new CompletableFuture<>();
        List<RemoteCall> results = Collections.synchronizedList(new ArrayList<>());
        AtomicInteger remaining = new AtomicInteger(tasks.size());

        // 异步收集结果
        CompletableFuture.runAsync(() -> {
            try {
                for (int i = 0; i < tasks.size(); i++) {
                    Future<RemoteCall> future = ecs.take();
                    RemoteCall result = future.get();
                    results.add(result);

                    // 如果这是最后一个任务, 完成 Future
                    if (remaining.decrementAndGet() == 0) {
                        // 状态转换: 将 CompletableFuture 从未完成状态变为已完成状态
                        // 设置结果: 同时将收集到的 results 列表设置为 CompletableFuture 的结果值
                        // 唤醒等待者: 唤醒所有在 get()、join() 上等待的线程
                        // 触发回调: 触发所有通过 thenAccept()、thenApply() 等注册的回调函数
                        resultFuture.complete(results);
                    }
                }
            } catch (Exception e) {
                resultFuture.completeExceptionally(e);
            }
        });
        return resultFuture;
    }
}

package thread.concurrency.task;

import cn.qingweico.concurrent.pool.ThreadObjectPool;

import java.util.concurrent.*;

/**
 * {@link Future} 提供了异步并行计算的功能: 1 多线程 2 有返回 3 异步任务
 * 优点: 显著提高程序的执行效率
 * 缺点: get()会阻塞当前线程 可以使用带有超时限制get() {@link FutureTask#get(long, TimeUnit)}
 *      isDone()轮询 如果想异步地获取结果,通常会使用轮询的方式去获取结果,虽然避免了阻塞,但是会耗费CPU资源
 * {@link Runnable}
 *
 * @author zqw
 * @date 2022/6/18
 * {@link Callable}
 * @see RunnableFuture {@code extends Future Runnable}
 * @see FutureTask {@code implements RunnableFuture}
 * @see FutureTask {@code FutureTask(Callable<V> callable)}
 * @see FutureTask {@code FutureTask(Runnable runnable, V result)}
 */
class SimpleFutureTask {
    static ExecutorService pool = ThreadObjectPool.newFixedThreadPool(2);

    public static void main(String[] args) {
        FutureTask<String> futureTask = new FutureTask<>(() -> {
            System.out.println("print call method");
            return "return call method";
        });
        pool.execute(futureTask);
        Future<?> nullRet = pool.submit(futureTask);
        Future<String> f = pool.submit(() -> {
            System.out.println("print thread pool callable task");
            return "return thread pool callable task";
        });
        try {
            // null
            System.out.println("pool submit runnable task: " + nullRet.get());
            System.out.println("pool submit callable task: " + f.get());
            System.out.println("execute: " + futureTask.get());
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        pool.shutdown();
    }
}

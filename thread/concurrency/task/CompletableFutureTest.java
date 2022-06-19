package thread.concurrency.task;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.testng.annotations.Test;
import thread.pool.CustomThreadPool;

import java.util.Arrays;
import java.util.concurrent.*;
import java.util.function.Supplier;

/**
 * CompletableFuture是对Future的改进,减少阻塞和轮询,其提供了一种观察者模式类似的机制,
 * 可以让任务执行完后通知监听的一方(回调函数), 其无论是正常执行完还是发生异常
 * <p>
 * {@link CompletableFuture}
 * {@link CompletionStage} 代表异步计算过程中的某一个阶段, 一个阶段完成后可能会触发另外一个阶段
 * {@link CompletableFuture#runAsync(Runnable)}
 * {@link CompletableFuture#runAsync(Runnable, Executor)}
 * {@link CompletableFuture#supplyAsync(Supplier)}
 * {@link CompletableFuture#runAsync(Runnable, Executor)}
 * 若没有指定线程池, 则使用默认的ForkJoinPool.commonPool()
 * xxxAsync与不带Async后缀的方法的区别是所使用的线程池会不同
 * @author zqw
 * @date 2022/2/5
 */
@Slf4j
public class CompletableFutureTest {

    @Test
    public void runAsync() {
        CompletableFuture<Void> cf = CompletableFuture.runAsync(() -> System.out.println("thread: " + Thread.currentThread().getName()));
        try {
            System.out.println(cf.get());
        } catch (ExecutionException | InterruptedException ex) {
            // ignore
        }
    }

    @Test
    public void runAsyncWithThreadPool() {
        ExecutorService pool = CustomThreadPool.newFixedThreadPool(1);
        CompletableFuture.runAsync(() -> System.out.println("thread: " + Thread.currentThread().getName()), pool);
    }

    @Test
    public void serial() {
        // handle() 和 thenApply() 不同点在于后者遇到异常会停止计算 而前者会继续计算 可以根据异常参数处理
        CompletableFuture<char[]> f0 = CompletableFuture.supplyAsync(() -> {
            System.out.println("thread: " + Thread.currentThread().getName());
            return "supplyAsync";
        }).thenApply((s) -> s + " thenApply").thenApply(String::toCharArray).whenComplete((v, e) -> {
            if (e == null) {
                System.out.println("task complete: " + Arrays.toString(v));
            }
        }).exceptionally(e -> {
            System.out.println(e.getMessage());
            return null;
        });
        // => CompletableFuture#get()会抛出异常
        // blocked and get the result.
        // 或者传入线程池参数, 手动关闭
        System.out.println(f0.join());
    }

    @Test
    public void getResult() throws ExecutionException, InterruptedException {
        CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                // ~
            }
            return "/api/";
        });
        // 立即获取结果,不会阻塞,若计算没有完成,则使用替代的结果(valueIfAbsent)
        System.out.println(cf.getNow("/tmp/"));
        // 是否打断get()/join()方法,为true则表示打断,则返回给定的参数值;false否表示没有打断,返回正常计算的值
        System.out.println(cf.complete("/tmp/") + cf.get());
    }

    @Test
    public void consumeResult() {
        CompletableFuture.supplyAsync(() -> {
            int r = ThreadLocalRandom.current().nextInt(10);
            System.out.printf("ret: %s%n", r);
            return r;
        }).thenApply(r -> r + 1).thenApply(r -> r + 2).thenAccept(System.out::println);
    }

    @Test
    public void andConverge() {
        CompletableFuture<String> f0 = CompletableFuture.supplyAsync(() -> "f0");
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> "f1");
        CompletableFuture<String> f2 = f0.thenCombine(f1, (var x, var y) -> x + y);
        System.out.println(f2.join());
        f0.thenAcceptBoth(f1, (var x, var y) -> System.out.println(x + y));
        f0.runAfterBoth(f1, () -> System.out.println("after f0 f1 done"));
    }

    @Test
    public void exception() {
        CompletableFuture<Integer> f0 = CompletableFuture.supplyAsync(() -> (1)).thenApply(r -> r * 10).exceptionally(e -> 0).whenComplete((r, e) -> {
            System.out.println("whenComplete: not return value");
            System.out.println(r);
        }).handle((r, t) -> r);
        System.out.println(f0.join());
    }

    @Test
    public void orConverge() {
        CompletableFuture<String> f0 = CompletableFuture.supplyAsync(() -> {
            int t = RandomUtils.nextInt(2, 5);
            log.info("f0 t: {}", t);
            try {
                TimeUnit.SECONDS.sleep(t);
            } catch (InterruptedException e) {
                log.error("sleep interrupted");
            }
            return "f0: " + t;
        });
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {
            int t = RandomUtils.nextInt(2, 5);
            log.info("f1 t: {}", t);
            try {
                TimeUnit.SECONDS.sleep(t);
            } catch (InterruptedException e) {
                log.error("sleep interrupted");
            }
            return "f1: " + t;
        });
        CompletableFuture<String> f2 = f0.applyToEither(f1, s -> s);
        System.out.println(f2.join());
    }
}

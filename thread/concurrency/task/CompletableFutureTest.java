package thread.concurrency.task;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import cn.qingweico.concurrent.thread.ThreadUtils;
import cn.qingweico.concurrent.UnsafeSupport;
import cn.qingweico.concurrent.pool.CustomizableThreadFactory;
import cn.qingweico.concurrent.pool.ThreadPoolBuilder;
import cn.qingweico.io.Print;
import cn.qingweico.supplier.RandomDataGenerator;
import cn.qingweico.constants.Symbol;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.*;
import java.util.function.Supplier;

/**
 * CompletableFuture是对Future的改进,减少阻塞和轮询,其提供了一种观察者模式类似的机制,
 * 可以让任务执行完后通知监听的一方(回调函数), 其无论是正常执行完还是发生异常
 * <p>
 * {@link CompletableFuture}
 * {@link CompletionStage} 代表异步计算过程中的某一个阶段, 一个阶段完成后可能会触发另外一个阶段
 * ------------------ runAsync 无返回值; supplyAsync 有返回值 ------------------
 * {@link CompletableFuture#runAsync(Runnable)}
 * {@link CompletableFuture#runAsync(Runnable, Executor)}
 * {@link CompletableFuture#supplyAsync(Supplier)}
 * {@link CompletableFuture#supplyAsync(Supplier, Executor)}
 * 若没有指定线程池, 则使用默认的ForkJoinPool.commonPool()[CommonPool的大小是CPU核数-1]
 * xxxAsync与不带Async后缀的方法的区别是同步与与异步的区别(如果注册时被依赖的操作已经完成,则直接由当前线程完成;如果注册时被依赖的操作还未完成,则由回调线程完成)
 *
 * @author zqw
 * @date 2022/2/5
 */
@Slf4j
public class CompletableFutureTest {

    private static final ExecutorService POOL = ThreadPoolBuilder.builder().threadFactory(CustomizableThreadFactory.basicThreadFactory()).build();

    private final ExecutorService single = ThreadPoolBuilder.builder().corePoolSize(1).build();

    @Test
    public void runAsync() {
        // 异步执行任务 没有返回值
        CompletableFuture<Void> cf = CompletableFuture.runAsync(() -> {
            UnsafeSupport.shortWait(2000);
            Print.grace("thread", Thread.currentThread().getName());
        });
        try {
            /*get和join的区别 : get方法会抛出异常且可以指定阻塞时间*/
            System.out.println(cf.get(1, TimeUnit.SECONDS));
            System.out.println(cf.join());
        } catch (ExecutionException | InterruptedException ex) {
            // ignore
        } catch (TimeoutException e) {
            Print.err(e);
        }
    }

    @Test
    public void runAsyncWithThreadPool() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> cf = CompletableFuture.runAsync(() -> Print.grace("thread", Thread.currentThread().getName()), POOL);
        System.out.println(cf.get());
    }

    @Test
    public void supplyAsync() {
        // 对比于runAsync有返回值
        CompletableFuture<String> cf = CompletableFuture.supplyAsync(ThreadUtils::getThreadName, POOL);
        System.out.println(cf.join());
    }

    @Test
    public void completedFuture() throws ExecutionException, InterruptedException {
        FutureTask<String> r1 = new FutureTask<>(() -> this.doGet(true));
        FutureTask<String> r2 = new FutureTask<>(() -> this.doGet(false));
        POOL.submit(r1);
        POOL.submit(r2);
        CompletableFuture<String> cf = CompletableFuture.completedFuture(r1.get());
        cf.thenAccept((result) -> {
            try {
                Print.grace(r2.get(), result);
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private String doGet(boolean i18n) {
        Faker faker;
        if (i18n) {
            UnsafeSupport.shortWait(2000);
            faker = new Faker(Locale.CHINA);
        } else {
            UnsafeSupport.shortWait(1000);
            faker = new Faker(Locale.ENGLISH);
        }
        return ThreadUtils.getThreadName() + Symbol.COLON + faker.book().author();
    }

    @Test
    public void newCompletableFuture() {
        // 通过 new 的方式创建
        CompletableFuture<String> cf = new CompletableFuture<>();
        cf.complete("completed");
        System.out.println(cf.join());
    }


    /**
     * 链式处理
     */

    @Test
    public void thenApply() {
        // 用于 future 完成后要执行的逻辑
        // 入参为上一个future执行的结果
        CompletableFuture<Integer> cf = CompletableFuture.supplyAsync(() -> 1).thenApply((result) -> 2 + result)
                .thenApply((result) -> 3 + result);
        System.out.println(cf.join());
    }

    @Test
    public void thenRun() {
        CompletableFuture<Void> cf = CompletableFuture.supplyAsync(() -> 1).thenRun(() -> {
            System.out.println("没有入参 没有返回值, 只是执行一段代码");
        });
        System.out.println(cf.join());
    }

    @Test
    public void thenAccept() {
        CompletableFuture<Void> cf = CompletableFuture
                .supplyAsync(() -> 1)
                .thenAccept((result) -> System.out.println("消费上一步的结果 没有返回值"));
        System.out.println(cf.join());
    }

    @Test
    public void thenCompose() {
        // thenCompose 用于两个 CompletableFuture 组合, 这两个 CompletableFuture 是先后依赖关系
        String firstStep = "第一步完成结果";
        String secondStep = "等待第一步完成";
        CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> firstStep);
        cf.thenCompose(result -> CompletableFuture.runAsync(() -> System.out.println(result + secondStep)));
    }

    @Test
    public void thenCombine() {
        // thenCombine 用于组合两个独立的CompletableFuture, 这两个 CompletableFuture 没有依赖关系, 且只有这两个都完成才会计算后续的结果
        CompletableFuture<Double> h = CompletableFuture.supplyAsync(() -> {
            UnsafeSupport.shortWait(2000);
            double height = 10.67d;
            Print.grace(ThreadUtils.getThreadName(), String.format("完成计算, 结果为 \033[34m%s\033[0m", height));
            return height;
        });
        CompletableFuture<Double> w = CompletableFuture.supplyAsync(() -> {
            UnsafeSupport.shortWait(3000);
            double weight = 5.12d;
            Print.grace(ThreadUtils.getThreadName(), String.format("完成计算, 结果为 \033[34m%s\033[0m", weight));
            return weight;

        });
        CompletableFuture<Double> combine = h.thenCombine(w, (height, weight) -> height * weight);
        System.out.println(combine.join());
    }

    /**
     * 异常处理
     */
    @Test
    public void exceptionally() {
        // 当 CompletableFuture 任意一步发生异常时都会进入该方法, 并返回异常发生时的默认值
        CompletableFuture<Integer> cf = CompletableFuture.supplyAsync(() -> 1)
                .thenApply((r) -> r + 2)
                .thenApply((r) -> r / (RandomUtils.nextBoolean() ? 0 : 1))
                .exceptionally(e -> {
                    System.out.println(e.getMessage());
                    return 0;
                });

        System.out.println(cf.join());

    }

    @Test
    public void handle() {
        CompletableFuture<Integer> handle = CompletableFuture.supplyAsync(() -> 1)
                .thenApply((r) -> r + 2)
                .thenApply((r) -> r / (RandomDataGenerator.rndBoolean() ? 0 : 1))
                .handle((r, e) -> {
                    if (e != null) {
                        System.out.println(e.getMessage());
                        return 0;
                    }
                    System.out.println("没有发生异常");
                    return r + 3;

                });
        System.out.println(handle.join());
    }

    @Test
    public void serial() {
        // handle() 和 thenApply() 不同点在于后者遇到异常会停止计算, 而前者会继续计算, 可以根据异常参数处理
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

    @Test
    public void anyOf() {
        // anyOf 任意一个任务执行成功就会返回 [INPUT : CompletableFuture数组]
        CompletableFuture<String> c1 = CompletableFuture.supplyAsync(() -> {
            UnsafeSupport.shortWait(10);
            return "c1";
        });
        CompletableFuture<String> c2 = CompletableFuture.supplyAsync(() -> {
            UnsafeSupport.shortWait(20);
            return "c2";
        });
        CompletableFuture<String> c3 = CompletableFuture.supplyAsync(() -> {
            UnsafeSupport.shortWait(30);
            return "c3";
        });
        // 使用join拿到结果
        CompletableFuture.anyOf(c1, c2, c3).whenComplete((v, e) -> System.out.println(v));
    }

    @Test
    public void allOf() {
        // allOf 所有任务都执行成功才能继续执行 但是返回值并没有提供所有异步结果 [INPUT : CompletableFuture数组]
        CompletableFuture<String> c1 = CompletableFuture.supplyAsync(ThreadUtils::getThreadName);
        CompletableFuture<String> c2 = CompletableFuture.supplyAsync(ThreadUtils::getThreadName);
        // 可以使用join拿到所有结果,再放到数组中
        List<CompletableFuture<String>> result = Arrays.asList(c1, c2);
        CompletableFuture.allOf(result.toArray(new CompletableFuture[0])).whenComplete((r, e) -> result.stream().map(CompletableFuture::join).toList().forEach(System.out::println));
    }

    @Test
    public void allOfTest() throws IOException {
        CompletableFuture<Boolean> sendSms = CompletableFuture.supplyAsync(() -> {
            sendSms();
            return true;
        }, POOL);
        CompletableFuture<Boolean> sendEmail = CompletableFuture.supplyAsync(() -> {
            sendEmail();
            return true;
        }, POOL);
        CompletableFuture<Void> future = CompletableFuture.allOf(sendSms, sendEmail);
        // thenRun 和 thenRunAsync 的区别 前者会继续使用上一个任务的线程池 后者会使用新的线程池(如果传入线程池的话 否则使用ForkJoinPool)
        future.thenRunAsync(() -> {
            try {
                if (sendSms.get() && sendEmail.get()) {
                    callback();
                }
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });
        log.info("request over...");
        // Simple block and nothing to do...
        System.out.println(System.in.read());
    }

    private void sendSms() {
        UnsafeSupport.shortWait(2000);
        log.info("endSms success...");
    }

    private void sendEmail() {
        UnsafeSupport.shortWait(3000);
        log.info("sendEmail success...");
    }

    private void callback() {
        log.info("callback success...");
    }

    @Test
    public void threadHunger() {
        // 线程饥饿 区别于死锁
        // 解决 : 嵌套的异步执行任务避免使用同一个线程池
        CompletableFuture<Void> future = CompletableFuture.runAsync(this::doSomething, single);
        future.join();
    }

    public void doSomething() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "do something", single);
        log.info(future.join());
    }

    /**
     * CompletableFuture在自定义线程池中的异常处理
     */
    @Test
    public void exceptionHandlerInThreadPool() {
        CompletableFuture.runAsync(() -> {
            throw new IllegalStateException("use exceptionally() handle");
        }, single).exceptionally(err -> {
            // 不要在 exceptionally 内部抛出新的异常, 因为这里的异常不会被处理, 且exceptionally方法没有返回值, 导致CompletableFuture进入异常状态
            log.error("{}", err.getMessage(), err);
            return null;
        });

        // handle可以同时处理正常结果和异常情况
        CompletableFuture.supplyAsync(() -> {
            throw new IllegalStateException("use handle() handle");
        }, single).handle((result, err) -> {
            if (err != null) {
                log.error("{}", err.getMessage(), err);
            }
            log.info("result: {}", result);
            return null;
        });
        // exceptionallyCompose 可以返回新的 CompletableFuture 进行异常恢复
    }

    // https://blog.csdn.net/sermonlizhi/article/details/123356877
    // https://mp.weixin.qq.com/s/ODjEpzFNozQJBDYQVtGlPA
}

package thread;

import org.apache.commons.lang3.RandomUtils;
import org.testng.annotations.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * CompletableFuture
 *
 * @author zqw
 * @date 2022/2/5
 */
public class CompletableFutureTest {
    @Test
    public void serial() {
        CompletableFuture<char[]> f0 = CompletableFuture.supplyAsync(() -> "supplyAsync")
                .thenApply((s) -> s + " thenApply")
                .thenApply(String::toCharArray);

        System.out.println(f0.join());
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
        CompletableFuture<Integer> f0 = CompletableFuture.supplyAsync(() -> (1))
                .thenApply(r -> r * 10)
                .exceptionally(e -> 0)
                .whenComplete((r, e) -> {
                    System.out.println("whenComplete: not return value");
                    System.out.println(r);
                })
                .handle((r, t) -> r);
        System.out.println(f0.join());
    }

    @Test
    public void orConverge() {
        CompletableFuture<String> f0 = CompletableFuture.supplyAsync(() -> {
            int t = RandomUtils.nextInt(2, 5);
            try {
                TimeUnit.SECONDS.sleep(t);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "f0: " + t;
        });
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {
            int t = RandomUtils.nextInt(2, 5);
            try {
                TimeUnit.SECONDS.sleep(t);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "f1: " + t;
        });

        CompletableFuture<String> f2 = f0.applyToEither(f1, s -> s);
        System.out.println(f2.join());
    }
}

package oak.stream;

import cn.qingweico.io.Print;

import java.util.Comparator;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

/**
 * Stream 并行流
 *
 * @author zqw
 * @date 2021/9/27
 */
class ParallelStream {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        var random = new Random();
        var list = IntStream.range(0, 1_000_000)
                .map(x -> random.nextInt(10_000))
                .boxed()
                .toList();

        var t0 = System.currentTimeMillis();
        System.out.println(list.stream().max(Comparator.comparingInt(a -> a)));
        Print.time("serial", System.currentTimeMillis() - t0);


        var t1 = System.currentTimeMillis();
        System.out.println(list.parallelStream().max(Comparator.comparingInt(a -> a)));
        Print.time("parallel", System.currentTimeMillis() - t1);


        var t2 = System.currentTimeMillis();
        var pool = new ForkJoinPool(2);
        System.out.println(pool.submit(() -> list.parallelStream().max(Comparator.comparingInt(a -> a))).get());
        Print.time("fork_join", System.currentTimeMillis() - t2);
    }
}

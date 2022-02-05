package oak.stream;

import java.util.Comparator;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author zqw
 * @date 2021/9/27
 */
public class ParallelStream {
   public static void main(String[] args) throws ExecutionException, InterruptedException {
      var random = new Random();
      var list = IntStream.range(0, 1_000_000)
              .map(x -> random.nextInt(10_000))
              .boxed()
              .collect(Collectors.toList());

      var t0 = System.currentTimeMillis();
      System.out.println(list.stream().max(Comparator.comparingInt(a -> a)));
      System.out.println("serial time: " + (System.currentTimeMillis() - t0) + "ms");


      var t1 = System.currentTimeMillis();
      System.out.println(list.parallelStream().max(Comparator.comparingInt(a -> a)));
      System.out.println("parallel time: " + (System.currentTimeMillis() - t1) + "ms");


      var t2 = System.currentTimeMillis();
      var pool = new ForkJoinPool(2);
      System.out.println(pool.submit(() -> list.parallelStream().max(Comparator.comparingInt(a -> a))).get());
      System.out.println("fork_join time: " + (System.currentTimeMillis() - t2) + "ms");
   }
}

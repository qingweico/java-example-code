package thinking.concurrency.share;

import thread.pool.ThreadPoolBuilder;
import util.Print;

import java.util.concurrent.ExecutorService;

/**
 * 偶数校验
 *
 * @author zqw
 * @date 2021/1/18
 */
public class EvenChecker implements Runnable {
    private final AbstractIntGenerator generator;
    private final int id;

    public EvenChecker(AbstractIntGenerator g, int ident) {
        generator = g;
        id = ident;
    }

    @Override
    public void run() {
        while (!generator.isCanceled()) {
            int val = generator.next();
            if (val % 2 != 0) {
                Print.println("ThreadName " + id + ": " + val + " not even");
                // Cancels all EvenCheckers
                generator.cancel();
            }
        }
    }

    // Test any type of IntGenerator

    public static void test(AbstractIntGenerator gp, int count) {
        Print.println("Press Control-C to exit");
        ExecutorService pool = ThreadPoolBuilder.builder().build();
        for (int i = 0; i < count; i++) {
            pool.execute(new EvenChecker(gp, i));
        }
        pool.shutdown();
    }

    // Default value for count

    public static void test(AbstractIntGenerator g) {
        test(g, 10);
    }
}

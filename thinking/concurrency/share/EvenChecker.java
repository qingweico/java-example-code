package thinking.concurrency.share;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static util.Print.print;

/**
 * @author zqw
 * @date 2021/1/18
 */
public class EvenChecker implements Runnable {
    private final IntGenerator generator;
    private final int id;

    public EvenChecker(IntGenerator g, int ident) {
        generator = g;
        id = ident;
    }

    @Override
    public void run() {
        while (!generator.isCanceled()) {
            int val = generator.next();
            if (val % 2 != 0) {
                print("ThreadName " + id + ": " + val + " not even");
                // Cancels all EvenCheckers
                generator.cancel();
            }
        }
    }

    // Test any type of IntGenerator
    public static void test(IntGenerator gp, int count) {
        print("Press Control-C to exit");
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < count; i++) {
            exec.execute(new EvenChecker(gp, i));
        }
        exec.shutdown();
    }

    // Default value for count
    public static void test(IntGenerator g) {
        test(g, 10);
    }
}

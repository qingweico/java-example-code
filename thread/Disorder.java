package thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The reason bytecode instructions can be out of order is to improve efficiency.
 * <p>
 * principle: Bytecode instructions can be exchanged on the premise that
 * it does not affect the final consistency of the single thread.
 *
 * @author zqw
 * @date 2021/1/30
 */
public class Disorder {
    private static int x = 0, y = 0, a = 0, b = 0;
    static ExecutorService pool = Executors.newCachedThreadPool();

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            CountDownLatch latch = new CountDownLatch(2);
            pool.execute(() -> {
                a = 1;
                x = b;
                latch.countDown();
            });

            pool.execute(() -> {
                b = 1;
                y = a;
                latch.countDown();
            });
            latch.await();

            if (x == 0 && y == 0) {
                System.err.println("第[" + i + "]次执行: " + "x = " + x + ", y = " + y);
                break;
            }
        }
    }
}

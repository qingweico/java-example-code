package thread;

import java.util.concurrent.CountDownLatch;

/**
 * The reason bytecode instructions can be out of order is to improve efficiency.
 * <p>
 * principle: Bytecode instructions can be exchanged on the premise that
 * it does not affect the final consistency of the single thread.
 *
 * @author:qiming
 * @date: 2021/1/30
 */
public class Disorder {
    private static int x = 0, y = 0, a = 0, b = 0;

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            x = 0;
            y = 0;
            a = 0;
            b = 0;
            CountDownLatch latch = new CountDownLatch(2);
            Thread one = new Thread(() -> {
                a = 1;
                x = b;
                latch.countDown();
            });

            Thread other = new Thread(() -> {
                b = 1;
                y = a;
                latch.countDown();
            });

            one.start();
            other.start();
            latch.await();

            if (x == 0 && y == 0) {
                System.err.println("第[" + i + "]次执行: " + "a = " + a + ", b = " + b);
                break;
            }
        }
    }
}

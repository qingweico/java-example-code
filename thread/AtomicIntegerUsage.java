package thread;

import java.util.concurrent.atomic.AtomicInteger;

import static util.Print.print;

/**
 * Alternate print ABCDEF 123456
 *
 * @author:qiming
 * @date: 2020/10/05
 */
public class AtomicIntegerUsage {
    static AtomicInteger threadNo = new AtomicInteger(1);

    public static void main(String[] args) {
        char[] numbers = "123456".toCharArray();
        char[] words = "ABCDEF".toCharArray();

        new Thread(() ->
        {
            for (char n : numbers) {
                while (threadNo.get() != 1) {}
                print(n);
                threadNo.set(2);
            }
        }, "t1").start();
        new Thread(() -> {
            for (char c : words) {
                while (threadNo.get() != 2) {}
                print(c);
                threadNo.set(1);
            }
        }, "t2").start();
    }
}

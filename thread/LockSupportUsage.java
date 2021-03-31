package thread;

import java.util.concurrent.locks.LockSupport;

/**
 * Alternate print ABCDEF 123456
 *
 * @author:qiming
 * @date: 2020/10/10
 */
public class LockSupportUsage {
    static Thread t1 = null, t2 = null;

    public static void main(String[] args) {
        char[] numbers = "123456".toCharArray();
        char[] words = "ABCDEF".toCharArray();
        t1 = new Thread(() ->
        {
            for (char n : numbers) {
                System.out.print(n);
                // Wake up the t2 thread
                LockSupport.unpark(t2);
                // Blocking the current thread
                LockSupport.park();
            }
        }, "t1");
        t2 = new Thread(() ->
        {
            for (char w : words) {
                // Blocking the current thread
                LockSupport.park();
                System.out.print(w);
                // Wake up the t1 thread
                LockSupport.unpark(t1);
            }
        }, "t2");


        t1.start();
        t2.start();
    }
}

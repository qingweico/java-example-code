package thread;

/**
 * 自旋锁
 * Alternate print ABCDEF 123456
 *
 * @author:qiming
 * @date: 2020/09/23
 */
public class CASUsage {
    enum ReadyToRun {T1, T2}

    static volatile ReadyToRun r = ReadyToRun.T1;

    public static void main(String[] args) {
        char[] numbers = "123456".toCharArray();
        char[] words = "ABCDEF".toCharArray();

        new Thread(() ->
        {
            for (char n : numbers) {
                while (r != ReadyToRun.T1) {}
                System.out.print(n);
                r = ReadyToRun.T2;
            }
        }, "t1").start();

        new Thread(() -> {
            for (char w : words) {
                while (r != ReadyToRun.T2) {}
                System.out.print(w);
                r = ReadyToRun.T1;
            }
        }, "t2").start();
    }
}

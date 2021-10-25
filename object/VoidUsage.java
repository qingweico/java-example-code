package object;

/**
 * Void
 * The void's wrapper class
 *
 * @author:qiming
 * @date: 2021/1/2
 */
public class VoidUsage {
    private static final int MIN_RANGE = 0;

    private static final int MAX_RANGE = 10;

    public static Void rangeCheck(int n) {
        if (n < MIN_RANGE || n > MAX_RANGE) {
            throw new IllegalArgumentException("n < " + MIN_RANGE + " || n > " + MAX_RANGE);
        }
        return null;
    }

    public static int getSum(int n) {
        return calculateAndCheck(n, rangeCheck(n));
    }

    public static int calculateAndCheck(int n, Void v) {
        return n + 10;
    }

    public static void main(String[] args) {
        System.out.println(getSum(100));
    }
}

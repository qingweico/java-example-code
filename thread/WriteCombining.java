package thread;

import annotation.Pass;
import util.Constants;

/**
 * 4-byte buffer inside the CPU
 *
 * @author zqw
 * @date 2022/2/11
 */
@Pass
@SuppressWarnings("all")
class WriteCombining {
    private static final int ITERATIONS = Integer.MAX_VALUE;
    private static final int ITEMS = 1 << 24;
    private static final int MASK = ITEMS - 1;
    private static final int COUNT = Constants.THREE;
    private static final byte[] A = new byte[ITEMS];
    private static final byte[] B = new byte[ITEMS];
    private static final byte[] C = new byte[ITEMS];
    private static final byte[] D = new byte[ITEMS];
    private static final byte[] E = new byte[ITEMS];
    private static final byte[] F = new byte[ITEMS];

    public static void main(final String[] args) {

        for (int i = 1; i <= COUNT; i++) {
            System.out.println(i + " SingleLoop duration (ns) = " + runCaseOne());
            System.out.println(i + " SplitLoop  duration (ns) = " + runCaseTwo());
        }
    }

    public static long runCaseOne() {
        long start = System.nanoTime();
        int i = ITERATIONS;

        while (--i != 0) {
            int slot = i & MASK;
            byte b = (byte) i;
            A[slot] = b;
            B[slot] = b;
            C[slot] = b;
            D[slot] = b;
            E[slot] = b;
            F[slot] = b;
        }
        return System.nanoTime() - start;
    }

    public static long runCaseTwo() {
        long start = System.nanoTime();
        int i = ITERATIONS;
        while (--i != 0) {
            int slot = i & MASK;
            byte b = (byte) i;
            A[slot] = b;
            B[slot] = b;
            C[slot] = b;
        }
        i = ITERATIONS;
        while (--i != 0) {
            int slot = i & MASK;
            byte b = (byte) i;
            D[slot] = b;
            E[slot] = b;
            F[slot] = b;
        }
        return System.nanoTime() - start;
    }
}

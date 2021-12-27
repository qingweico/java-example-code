package util;

/**
 * @author:qiming
 * @date: 2021/7/11
 */
public final class Mathematics {
    // Calculate the PI
    public static double calcPi() {
        double re = 0;
        for (int i = 1; i < 10000; i++) {
            re += ((i & 1) == 0 ? -1 : 1) * 1.0 / (2 * i - 1);
        }
        return re * 4;
    }

    public static void forLoop() {
        int i;
        int n = 1;
        for (i = 1; n != 2; i++) {
            System.out.println(i);
            ++n;
        }
        //1 2
        System.out.println(i);
    }

    public static int fastPower(int a, int b, int m) {
        int ans = 1;
        while (b > 0) {
            if ((b & 1) == 1) {
                ans = ans * a % m;
            }
            a = a * a % m;
            b = b >> 1;
        }
        return ans;

    }

    /**
     * variable-precision SWAR
     * @param i the input number(decimal)
     * @return the number of one in binary
     */
    public static int swar(int i) {
        i = (i & 0x55555555) + ((i >> 1) & 0x55555555);

        i = (i & 0x33333333) + ((i >> 2) & 0x33333333);

        i = (i & 0x0f0f0f0f) + ((i >> 4) & 0x0f0f0f0f);

        i = (i * (0x01010101) >> 24);

        return i;
    }
}

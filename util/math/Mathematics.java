package util.math;

import util.constants.Constants;

/**
 * @author zqw
 * @date 2021/7/11
 */
public final class Mathematics {
    /**
     * Calculate the PI
     */
    public static double calcPi() {
        double re = 0;
        for (int i = 1; i < Constants.NUM_10000; i++) {
            re += ((i & 1) == 0 ? -1 : 1) * 1.0 / (2 * i - 1);
        }
        return re * 4;
    }

    /**
     * 快速幂
     * @param a 底数
     * @param b 指数
     * @return 幂
     */
    public static int fastPower(int a, int b) {
        int ans = 1;
        while (b > 0) {
            if ((b & 1) == 1) {
                ans = ans * a;
            }
            a = a * a;
            b = b >> 1;
        }
        return ans;

    }

    /**
     * 计算数字{@code i}二进制中1的个数
     * variable-precision SWAR, Redis中 BITCOUNT 命令实现
     * @param i the input number(decimal)
     * @return the number of one in binary
     * @see Integer#bitCount, 另一种牛逼实现
     */
    public static int swar(int i) {
        i = (i & 0x55555555) + ((i >> 1) & 0x55555555);
        i = (i & 0x33333333) + ((i >> 2) & 0x33333333);
        i = (i & 0x0f0f0f0f) + ((i >> 4) & 0x0f0f0f0f);
        i = (i * (0x01010101) >> 24);
        return i;
    }

    public static void main(String[] args) {
        System.out.println(calcPi());
        System.out.println(fastPower(2, 10));
        System.out.println(swar(15));
        System.out.println( Integer.bitCount(15));
    }
}

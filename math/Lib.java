package math;

/**
 * @author:qiming
 * @date: 2021/1/18
 */
public class Lib {
    // Calculate the PI
    public static double calcPi() {
        double re = 0;
        for (int i = 1; i < 10000; i++) {
            re += ((i & 1) == 0 ? -1 : 1) * 1.0 / (2 * i - 1);
        }
        return re * 4;
    }
}

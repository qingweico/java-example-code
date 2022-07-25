package algorithm.maths;

/**
 * 返回一个数的绝对值
 *
 * @author zqw
 * @date 2022/7/18
 */
public class AbsoluteValue {

    /**
     * Returns the absolute value of a number.
     *
     * @param number The number to be transformed
     * @return The absolute value of the {@code number}
     */
    public static int getAbsValue(int number) {
        return number < 0 ? -number : number;
    }
}

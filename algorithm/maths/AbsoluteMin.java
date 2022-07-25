package algorithm.maths;

import java.util.Arrays;

/**
 *  获取最小值从给定的参数中
 *
 * @author zqw
 * @date 2022/7/18
 * {@link AbsoluteMax}
 */
public class AbsoluteMin {

    /**
     * Compares the numbers given as arguments to get the absolute min value.
     *
     * @param numbers The numbers to compare
     * @return The absolute min value
     */
    public static int getMinValue(int... numbers) {
        if (numbers.length == 0) {
            throw new IllegalArgumentException("Numbers array cannot be empty");
        }

        var absMinWrapper = new Object() {
            int value = numbers[0];
        };

        Arrays.stream(numbers)
                .skip(1)
                .filter(number -> Math.abs(number) < Math.abs(absMinWrapper.value))
                .forEach(number -> absMinWrapper.value = number);

        return absMinWrapper.value;
    }
}

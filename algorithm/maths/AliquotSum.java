package algorithm.maths;

import java.util.stream.IntStream;

/**
 *  In number theory, the aliquot sum s(n) of a positive integer n is the sum of
 *  all proper divisors of n, that is, all divisors of n other than n itself. For
 *  example, the proper divisors of 15=(that is, the positive divisors of 15 that
 *  are not equal to 15) are 1, 3 and 5, so the aliquot sum of 15 is 9 i.e. (1 +
 *  3 + 5). Wikipedia: <a href="https://en.wikipedia.org/wiki/Aliquot_sum">Aliquot_sum</a>
 *
 * @author zqw
 * @date 2022/7/18
 */
public class AliquotSum {

    /**
     * Finds the aliquot sum of an integer number.
     *
     * @param number a positive integer
     * @return aliquot sum of given {@code number}
     */
    public static int getAliquotValue(int number) {
        var sumWrapper = new Object() {
            int value = 0;
        };

        IntStream.iterate(1, i -> ++i)
                .limit(number / 2)
                .filter(i -> number % i == 0)
                .forEach(i -> sumWrapper.value += i);

        return sumWrapper.value;
    }
}

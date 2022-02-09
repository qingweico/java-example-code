package thinking;

import util.Constants;

import java.util.Arrays;
import java.util.Random;

/**
 * @author zqw
 * @date 2020/10/23
 */
public class IceCream {
    private static final Random RANDOM = new Random(47);
    static final String[] FLAVORS = {
            "Chocolate", "Strawberry",
            "Vanilla Fudge Swirl", "Mint Chip",
            "Mocha Almond Fudge", "Rum Raisin",
            "Praline Cream", "Mud pie"
    };

    public static String[] flavorSet(int n) {
        if (n > FLAVORS.length) {
            throw new IllegalArgumentException("Set too big!");
        }
        String[] result = new String[n];
        boolean[] picked = new boolean[FLAVORS.length];
        for (int i = 0; i < n; i++) {
            int t;
            do {
                t = RANDOM.nextInt(FLAVORS.length);
            } while (picked[t]);
            result[i] = FLAVORS[t];
            picked[i] = true;
        }
        return result;
    }

    public static void main(String[] args) {
        for (int i = 0; i < new Random().nextInt(Constants.TEN); i++) {
            System.out.println(Arrays.deepToString(flavorSet(3)));
        }
    }
}

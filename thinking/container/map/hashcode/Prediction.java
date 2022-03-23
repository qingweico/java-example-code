package thinking.container.map.hashcode;

import java.util.Random;

/**
 * Predicting the weather with groundhogs.
 *
 * @author zqw
 * @date 2021/2/24
 */
public class Prediction {
    private static final Random R = new Random(47);

    private final boolean shadow = R.nextDouble() > 0.5;

    @Override
    public String toString() {
        return shadow ? "Six more weeks o Winter!" : "Early Spring!";
    }
}

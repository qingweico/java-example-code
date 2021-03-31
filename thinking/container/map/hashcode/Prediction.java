package thinking.container.map.hashcode;

import java.util.Random;

/**
 * @author:周庆伟
 * @date: 2021/2/24
 */
// Predicting the weather with groundhogs.
public class Prediction {
    private static final Random random = new Random(47);

    private final boolean shadow = random.nextDouble() > 0.5;

    public String toString() {
        return shadow ? "Six more weeks o Winter!" : "Early Spring!";
    }
}

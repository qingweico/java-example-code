package thinking.concurrency.share;

/**
 * @author zqw
 * @date 2021/1/19
 */
//When threads collide
public class EvenGenerator extends AbstractIntGenerator {

    private int currentEvenValue = 0;
    @Override
    public int next() {
        ++currentEvenValue;
        ++currentEvenValue;
        return currentEvenValue;
    }

    public static void main(String[] args) {
        EvenChecker.test(new EvenGenerator());
    }
}

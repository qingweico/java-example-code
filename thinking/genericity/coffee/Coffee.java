package thinking.genericity.coffee;

/**
 * @author zqw
 * @date 2021/3/23
 */
public class Coffee {
    private static long count = 0;
    private final long id = count++;
    @Override
    public String toString() {
        return getClass().getSimpleName() + " " + id;
    }
}

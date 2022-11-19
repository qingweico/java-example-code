package thinking.util;

/**
 * Tuple:
 * A single object in which a group of objects is packaged directly
 *
 * @author zqw
 * @date 2021/3/23
 */
public class TwoTuple<A, B> {
    public final A first;
    public final B second;

    public TwoTuple(A a, B b) {
        first = a;
        second = b;
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }
}

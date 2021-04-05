package thinking.inner;

/**
 * Holding a sequence of Objects
 *
 * @author:qiming
 * @date: 2021/1/29
 */
public interface Selector {
    boolean end();
    Object current();
    void next();
}

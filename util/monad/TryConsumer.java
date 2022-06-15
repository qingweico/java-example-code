package util.monad;

/**
 * @author zqw
 * @date 2021/9/15
 */
public interface TryConsumer<T, E extends Throwable> {


    /**
     * Performs this operation on the given argument.
     *
     * @param t the input argument
     * @throws E An exception {@code E extends Throwable}
     */
    void accept(T t) throws E;

}
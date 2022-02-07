package util.monad;

/**
 * @author zqw
 * @date 2021/9/15
 * @see java.util.function.Function
 */
public interface TryMapFunction<T, R> {
    /**
     * Applies this function to the given argument.
     *
     * @param t the function argument
     * @throws Throwable Throwable
     * @return the function result
     */
    R apply(T t) throws Throwable;
}
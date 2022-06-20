package util.monad;

/**
 * @author zqw
 * @date 2021/9/21
 */
public interface TrySupplier<T>{
    /**
     * Producing the type of the T
     * @return T
     * @throws Throwable Throwable {@link Throwable}
     */
    T get() throws Throwable;
}

package util;

/**
 * A generic interface
 *
 * @author zqw
 * @date 2021/3/23
 */

@FunctionalInterface
public interface Generator<T> {
    /**
     * Generate next element
     * @return The generic type {@code T}
     */
    T next();
}

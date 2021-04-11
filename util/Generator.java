package util;

/**
 * @author:qiming
 * @date: 2021/3/23
 */

// A generic interface
@FunctionalInterface
public interface Generator<T> {
    T next();
}

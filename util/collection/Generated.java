package util.collection;

import util.Generator;

/**
 * @author zqw
 * @date 2021/4/8
 */
public class Generated {
    /**
     * Fill an existing array:
     */
    public static <T> T[] array(T[] a, Generator<T> gen) {
        return new CollectionData<>(gen, a.length).toArray(a);
    }

    /**
     * Create a new array:
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] array(Class<T> type,
                                Generator<T> gen, int size) {
        T[] a = (T[]) java.lang.reflect.Array.newInstance(type, size);
        return new CollectionData<>(gen, size).toArray(a);
    }
}
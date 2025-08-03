package thinking.genericity;

import cn.qingweico.supplier.Generator;

/**
 * Automatically create a generator, give a class with a default(no-args) constructor
 *
 * @author zqw
 * @date 2021/4/10
 */
public class BasicGenerator<T> implements Generator<T> {
    private final Class<T> type;

    public BasicGenerator(Class<T> type) {
        this.type = type;
    }

    @Override
    public T next() {
        try {
            // Assume type is a public class
            return type.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> Generator<T> create(Class<T> type) {
        return new BasicGenerator<>(type);
    }
}

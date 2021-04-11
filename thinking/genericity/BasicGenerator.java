package thinking.genericity;

import util.Generator;

/**
 * Automatically create a generator, give a class with a default(no-args) constructor
 *
 * @author:qiming
 * @date: 2021/4/10
 */
public class BasicGenerator<T> implements Generator<T> {
    private final Class<T> type;

    public BasicGenerator(Class<T> type) {
        this.type = type;
    }

    @Override
    public T next() {
        try {
            // Assume type is a pubic class
            return type.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> Generator<T> create(Class<T> type) {
        return new BasicGenerator<>(type);
    }

    public static void main(String[] args) {
        Generator<CounterObject> gen = BasicGenerator.create(CounterObject.class);
        for (int i = 0; i < 5; i++) {
            System.out.println(gen.next());
        }
    }
}

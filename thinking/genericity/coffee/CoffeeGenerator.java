package thinking.genericity.coffee;

import util.Generator;

import java.util.Iterator;
import java.util.Random;

import static util.Print.print;

/**
 * @author zqw
 * @date 2021/3/23
 */
// Generate different types of Coffee
public class CoffeeGenerator<Coffee> implements Generator<Coffee>, Iterable<Coffee> {
    private final Class<?>[] types = {
            Latte.class,
            Mocha.class,
            Cappuccino.class,
            Americano.class,
            Breve.class
    };
    private static final Random rand = new Random(47);

    public CoffeeGenerator() {
    }

    // For iteration
    private int size = 0;

    public CoffeeGenerator(int size) {
        this.size = size;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Coffee next() {
        try {
            return (Coffee) types[rand.nextInt(types.length)].getDeclaredConstructor().newInstance();
            // Report programmer errors at run time
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    class CoffeeIterator implements Iterator<Coffee> {
        int count = size;

        @Override
        public boolean hasNext() {
            return count > 0;
        }

        @Override
        public Coffee next() {
            count--;
            return CoffeeGenerator.this.next();
        }

        @Override
        public void remove() {
            // Not implemented
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public Iterator<Coffee> iterator() {
        return new CoffeeIterator();
    }

    public static void main(String[] args) {
        CoffeeGenerator<Object> gen = new CoffeeGenerator<>();
        for (int i = 0; i < 5; i++) {
            print(gen.next());
        }
        System.out.println();
        CoffeeGenerator<thinking.genericity.coffee.Coffee > coffeeGenerator = new CoffeeGenerator<>(5);
        coffeeGenerator.forEach(System.out::println);
    }


}


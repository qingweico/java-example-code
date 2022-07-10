package thinking.genericity;


import java.util.Objects;

/**
 * @author zqw
 * @date 2021/1/12
 */
public class Holder<T> {
    private T value;

    public Holder() {
    }

    public Holder(T val) {
        value = val;
    }

    public void set(T val) {
        value = val;
    }

    public T get() {
        return value;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Holder)) return false;
        return value.equals(o);
    }

    public static void main(String[] args) {
        Holder<Apple> appleHolder = new Holder<>(new Apple());
        Apple a = appleHolder.get();
        appleHolder.set(a);
        // Cannot upcast
        // Holder<Fruit> fruitHolder = appleHolder;
        Holder<? extends Fruit> fruit = appleHolder;
        Fruit f = fruit.get();
        a = (Apple) fruit.get();
        try {
            Orange o = (Orange) fruit.get();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        // Cannot call set()
        // fruit.set(new Apple());
        // fruit.set(new Fruit());
        System.out.println(fruit.equals(a));


    }
}

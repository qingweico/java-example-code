package thinking.genericity;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Holder)) {
            return false;
        }
        return value.equals(o);
    }

    public static void main(String[] args) {
        Holder<Apple> appleHolder = new Holder<>(new Apple());
        Apple a = appleHolder.get();
        appleHolder.set(a);
        // Cannot upcast
        // Holder<Fruit> fruitHolder = appleHolder;

        Holder<? extends Fruit> fruit = new Holder<>(new Apple());
        // 泛型擦除为 Fruit(只能使用上界类型接受 没有上界类型则是Object) 运行时动态获取实际类型
        Fruit f = fruit.get();
        System.out.println(f.getClass());
        /// 向下转型为 Apple
        a = (Apple) fruit.get();
        System.out.println(a);
        try {
            // error,apple not an orange!
            Orange o = (Orange) fruit.get();
            System.out.println(o);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        // Cannot call set()
        // 由于使用上界通配符extends: 生产者 向外提供数据 不适用插入数据(消费者)
        /*fruit.set(new Apple());*/
        /*fruit.set(new Fruit());*/
        System.out.println(fruit.getClass());
        System.out.println(a.getClass());
    }
}

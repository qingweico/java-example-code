package thinking.genericity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author:周庆伟
 * @date: 2021/1/14
 */
public class GenericReading {
    static <T> T readExact(List<T> list) {
        return list.get(0);

    }

    static List<Apple> apples = Arrays.asList(new Apple());
    static List<Fruit> fruit = Arrays.asList(new Fruit());

    static void f1() {
        Apple a = readExact(apples);
        Fruit f = readExact(fruit);
        f = readExact(apples);
    }

    static class Reader<T> {
        T readExact(List<T> list) {
            return list.get(0);
        }
    }

    static void f2() {
        Reader<Fruit> fruitReader = new Reader<>();
        Fruit f = fruitReader.readExact(fruit);
        // Error readExact(List<Fruit>) cannot be applied to (List<Apple>)
        // Fruit a = fruitReader.readExact(apples);
    }

    static class CovariantReader<T> {
        T readCovariant(List<? extends T> list) {
            return list.get(0);
        }
    }

    static void f3() {
        CovariantReader<Fruit> fruitReader = new CovariantReader<>();
        Fruit f = fruitReader.readCovariant(fruit);
        Fruit a = fruitReader.readCovariant(apples);
    }

    public static void main(String[] args) {
        f1();
        f2();
        f3();
    }


}

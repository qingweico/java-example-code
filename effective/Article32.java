package effective;

import annotation.NotSafeType;
import annotation.SafeType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 谨慎并用泛型和可变参数
 *
 * @author zqw
 * @date 2021/11/16
 */
class Article32 {

    // Mixing generics and varargs can violate type safety!

    @SafeVarargs
    static void dangerous(List<String>... stringLists) {
        List<Integer> intList = List.of(42);
        ((Object[]) stringLists)[0] = intList;
        // Heap pollution: when a variables of a parameterized type refers to
        // a variables that is not of that type.
        String s = stringLists[0].get(0);
        // ClassCastException
        System.out.println(s);
    }

    // UNSAFE -- Exposes a reference to its generic parameter array!
    @NotSafeType
    static <T> T[] toArray(T... args) {
        return args;

    }

    static <T> T[] pickTwo(T a, T b, T c) {
        switch (ThreadLocalRandom.current().nextInt(3)) {
            case 0:
                return toArray(a, b);
            case 1:
                return toArray(a, c);
            case 2:
                return toArray(b, c);
        }
        throw new AssertionError();
    }

    static <T> List<T> pickTwoTypeSafety(T a, T b, T c) {
        switch (ThreadLocalRandom.current().nextInt(3)) {
            case 0:
                return List.of(a, b);
            case 1:
                return List.of(a, c);
            case 2:
                return List.of(b, c);
        }
        throw new AssertionError();
    }

    // Safe method with a generic varargs parameter
    // @SafeVarargs: this is valid only in static and final instance methods in JDK8!
    // otherwise, it is also legal in private instance method in JDK9.
    @SafeVarargs
    static <T> List<T> flatten(List<? extends T>... lists) {
        List<T> result = new ArrayList<>();
        for (List<? extends T> list : lists) {
            result.addAll(list);
        }
        return result;
    }

    // List as a typesafe alternative to a generic varargs parameter
    @SafeType
    static <T> List<T> flatten(List<List<? extends T>> lists) {
        List<T> result = new ArrayList<>();
        for (List<? extends T> list : lists) {
            result.addAll(list);
        }
        return result;
    }

    public static void main(String[] args) {
        String[] strings = pickTwo("Good", "Fast", "Cheap");
        // ClassCastException
        System.out.println(Arrays.toString(strings));
        List<String> attributes = pickTwoTypeSafety("Spring", "Summer", "White");
        System.out.println(attributes);
        List<Integer> integers = List.of(1, 2);
        List<Double> doubles = List.of(1d, 2d);
        List<Float> floats = List.of(1f, 2f);
        List<Number> audience = flatten(List.of(integers, doubles, floats));
        audience.forEach(System.out::println);
    }
}

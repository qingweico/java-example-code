package effective;

import java.util.*;
import java.util.concurrent.ScheduledFuture;

import static util.Print.print;

/**
 * 利用有限制通配符提升API灵活性
 *
 * @author zqw
 * @date 2021/11/17
 * @see Stack
 * @see Article30#max
 */
class Article31 {


    public static void main(String[] args) {
        Stack<Number> stack = new Stack<>();
        Collection<Integer> c = Arrays.asList(1, 2, 3);
        stack.pushAll(c);
        print(c);
        Collection<Object> dst = new ArrayList<>();
        stack.popAll(dst);
        print(dst);

        Set<Integer> integers = Set.of(1, 3, 5);
        Set<Double> doubles = Set.of(2.0, 4.0, 6.0);
        Set<Number> numbers = Article30.union(integers, doubles);
        // Explicit type parameter - require prior to Java 8
        // Set<Number> numbers = Article30.<Number>union(integers, doubles);
        print(numbers);

        List<ScheduledFuture<?>> scheduledFutures = new ArrayList<>();
        Article30.max(scheduledFutures);

    }

    public static <E> void swap(List<E> list, int i, int j) {
        list.set(i, list.set(j, list.get(i)));
    }

    // you can replace a type parameter with a wildcard
    // if it appears only once in the method declaration.

    public static void swap(int i, int j, List<?> list) {
        /*can't compile*/
        // list.set(i, list.set(j, list.get(i)));

        swapHelper(list, i, j);

    }

    private static <E> void swapHelper(List<E> list, int i, int j) {
        list.set(i, list.set(j, list.get(i)));

    }
}

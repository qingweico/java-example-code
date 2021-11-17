package effective;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import static util.Print.print;

/**
 * 利用有限制通配符提升API灵活性
 *
 * @author:qiming
 * @date: 2021/11/17
 * @see Stack
 */
public class Article31 {


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

    }
}

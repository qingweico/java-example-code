package effective;

import java.util.HashMap;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 方法引用优先于Lambda
 *
 * @author:qiming
 * @date: 2021/2/20
 */
public class Article43 {
    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        map.put("key", 2);
        // When JDK8 adding
        // If the specified key is not mapped, the method inserts the specified value, if
        // there's a mapping, the merge() method applies the specified function to the current
        // value and the specified value, and the result overrides the current value.
        map.merge("key", 1, (count, incr) -> count + incr);

        // A better approach
        map.merge("key", 1, Integer::sum);
        System.out.println(map.get("key"));

        System.out.println(Function.identity());
        Stream<String> stream = Stream.of("I", "love", "you", "too");
        Map<String, Integer> toMap = stream.collect(Collectors.toMap(Function.identity(), String::length));
        System.out.println(toMap);


        // Seeing src/onjava8/MessageBuilder.java
        // Lambda is preferred if the method and Lambda are in the same class.

        // Static reference
        // Integer::parseInt  >  str -> Integer.parseInt(str);

        // Restricted reference
        // Instant.now()::isAfter > Instant then = Instant.now(); t -> then.isAfter(t);

        // Unrestricted reference
        // String::toLowerCase > str -> String.toLowerCase

        // Class constructor reference
        // TreeMap<K, V>::new > () -> new TreeMap<K, V>

        // Array constructor reference
        // int[] a = int[]::new -> len -> new int[len]


        // Use method references whenever they are more concise and clear,
        // stick with Lambda if method references are not terse.


    }
}

package oak.stream;

import org.testng.annotations.Test;
import util.Print;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static util.Print.print;

/**
 * Stream: Intermediate operations; Terminal operations
 * Intermediate operations:
 * map; peek; filter; unordered >> stateless
 * sorted; skip; limit; distinct >> stateful
 * Terminal operations:
 * Un-short-circuiting: foreachOrdered; forEach; toArray; reduce; collect; max; min; count;
 * Short-circuiting: allMatch; anyMatch; findFirst; findAny; noneMatch;
 * pure function is the function with no side effects and impure function is opposite.
 *
 * @author zqw
 * @date 2021/2/5
 */
public class OperateStreamTest {

    final List<Integer> container = Arrays.asList(9, 0, 1, 3, 5, 7, 8, 8);

    @Test
    public void optional() {
        Optional<Integer> x = Optional.empty();
        var y = x.map(i -> i * i);
        // orElseThrow(); 如果没有值则抛出NoSuchElementException, 否则返回一个值
        Optional<Integer> ret = Optional.of(1);
        System.out.println(ret.orElseThrow());
        x.ifPresentOrElse(System.out::println, () -> System.out.println("no such element found"));
        System.out.println(y);
        // 集合判空处理
        // Optional.ofNullable(null).orElse(Collections.emptyList())
    }

    @Test
    public void statefulStream() {
        container.stream().sorted().forEach(Print::prints);
        print();
        container.stream().limit(3).forEach(Print::prints);
        print();
        container.stream().skip(3).forEach(Print::prints);
        print();
        // The equals() and hashCode() methods need to be overridden when used on objects.
        container.stream().distinct().forEach(Print::prints);
    }

    @Test
    public void generate() {
        Stream.iterate(0, x -> x + 4).limit(5).forEach(Print::print);
        String payload = IntStream.rangeClosed(1, 10).mapToObj(__ -> "a")
                .collect(Collectors.joining("")) + UUID.randomUUID();
        print(payload);
        Stream.generate(Math::random).limit(5).forEach(Print::print);
        IntStream s = IntStream.iterate(0, x -> x + 4).limit(4);
        print(Arrays.toString(s.toArray()));
    }

    @Test
    public void statelessStream() {
        // map and reduce
        Optional<Integer> reduce = container.stream().map(x -> x * 2).filter(x -> x > 10).reduce(Math::min);
        reduce.ifPresent(System.out::println);
        container.stream().unordered().peek((e) -> System.out.print("peek: " + e)).forEach(System.out::println);
    }

    @Test
    public void shortCircuiting() {

        // Unordered, forEachOrdered() used with parallelStream() usually.
        container.parallelStream().forEach(Print::prints);
        print();
        // Ordered
        container.parallelStream().forEachOrdered(Print::prints);

        System.out.println(Arrays.toString(container.stream().map((x) -> x + 1).toArray(Integer[]::new)));
        long count = container.stream().map((x) -> x + 1).collect(Collectors.toList())
                .stream().max(Integer::compareTo)
                .stream().min(Integer::compareTo)
                .stream().count();

        System.out.println(count);
    }

    @Test
    public void unShortCircuiting() {
        boolean existNegative = container.stream().anyMatch((x) -> x < 0);
        boolean allPositive = container.stream().allMatch((x) -> x > 0);
        System.out.println("existNegative: " + existNegative);
        System.out.println("allPositive: " + allPositive);
        Optional<Integer> first = container.stream().findFirst();
        first.ifPresent(System.out::println);
        Optional<Integer> any = container.stream().findAny();
        any.ifPresent(System.out::println);
        boolean allNegative = container.stream().noneMatch((x) -> x >= 0);
        System.out.println("allNegative: " + allNegative);
    }

    @Test
    public void flatMap() {
        // General
        List<String> stringList = Arrays.asList("hello", "world");
        Stream<Stream<Character>> streamStream = stringList
                .stream()
                .map(OperateStreamTest::filterCharacter);

        streamStream.forEach((stream) -> stream.forEach(Print::prints));
        print();

        // Using flatMap
        stringList.stream().flatMap(OperateStreamTest::filterCharacter).forEach(Print::prints);
    }

    private static Stream<Character> filterCharacter(String str) {
        List<Character> res = new ArrayList<>();
        for (Character c : str.toCharArray()) {
            res.add(c);
        }
        return res.stream();
    }

    @Test
    public void transfer() {
        var set = Stream.of("hello", "world")
                .flatMap(str -> str.chars().mapToObj((i) -> (char) i))
                .collect(Collectors.toSet());
        System.out.println(set);
    }
}

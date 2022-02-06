package oak.stream;

import org.testng.annotations.Test;
import util.Print;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static util.Print.print;

/**
 * map; reduce >> stateless
 * sorted; skip; limit >> stateful
 * pure function is the function with no side effects and impure function is opposite.
 *
 * @author zqw
 * @date 2021/2/5
 */
public class OperateStreamTest {

    final List<Integer> container = Arrays.asList(0, 1, 3, 5, 7, 8, 8);

    @Test
    public void optional() {
        Optional<Integer> x = Optional.empty();
        var y = x.map(i -> i * i);
        // orElseThrow()
        x.ifPresentOrElse(System.out::println, () -> {
            System.out.println("no such element found");
        });
        System.out.println(y);
    }

    @Test
    public void statefulStream() {
        container.stream().sorted().filter(x -> x % 2 == 0).forEach(Print::prints);
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
        Stream.iterate(0, x -> x + 4).limit(5).forEach(Print::prints);
        print();
        Stream.generate(Math::random).limit(5).forEach(Print::print);
        IntStream s = IntStream.iterate(0, x -> x + 4).limit(4);
        print(Arrays.toString(s.toArray()));
    }

    @Test
    public void statelessStream() {
        // map and reduce
        Optional<Integer> reduce = container.stream().map(x -> x * 2).reduce(Math::min);
        reduce.ifPresent(System.out::println);
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

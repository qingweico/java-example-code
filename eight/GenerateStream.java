package eight;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static util.Print.print;

/**
 * @author zqw
 * @date 2021/2/5
 */
public class GenerateStream {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(Arrays.asList(0, 2, 4, 6, 7, 8));
        Stream<Integer> stream = list.stream();
        stream.forEach(System.out::print);
        print();

        int[] arr = new int[]{2, 4, 5, 67};
        IntStream arrStream = Arrays.stream(arr);
        arrStream.forEach(System.out::print);
        print();

        Stream<String> stringStream = Stream.of("er", "out", "gen");
        stringStream.forEach(System.out::println);

        Stream.iterate(0, x -> x + 4).limit(5).forEach(System.out::println);

        Stream.generate(Math::random).limit(5).forEach(System.out::println);
    }
}

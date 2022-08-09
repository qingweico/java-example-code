package coretech2.date;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Random;;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author zqw
 * @date 2022/8/2
 */
public class TimeLine {
    public static void main(String[] args) {
        Instant start = Instant.now();
        fastAlgorithm();
        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start,end);
        long millions = timeElapsed.toMillis();
        System.out.printf("[fastAlgorithm]: %d milliseconds\n", millions);

        start = Instant.now();
        slowAlgorithm();
        end = Instant.now();
        Duration te = Duration.between(start,end);
        millions = te.toMillis();
        System.out.printf("[slowAlgorithm]: %d milliseconds\n", millions);
        boolean overTenTimeFaster = timeElapsed.multipliedBy(10).minus(te).isNegative();
        System.out.println(overTenTimeFaster);

    }

    public static void fastAlgorithm() {
        int size = 10;
        List<Integer> list = new Random().ints().map(i -> i % 100)
                .limit(size)
                .boxed().sorted().collect(Collectors.toList());
        System.out.println(list);
    }
    public static void slowAlgorithm() {
        int size = 10;
        List<Integer> list = new Random().ints().map(i -> i % 100)
                .limit(size)
                .boxed().sorted().collect(Collectors.toList());
        while (!IntStream.range(1, list.size())
                .allMatch(i -> list.get(i).compareTo(list.get(i - 1)) <= 0)) {
            Collections.shuffle(list);
        }
        System.out.println(list);
    }
}

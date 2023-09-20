package coretech2.stream;

import cn.hutool.core.date.StopWatch;
import collection.iter.RandomStringGenerator;
import org.junit.Test;
import util.Print;
import util.Tools;
import util.constants.Constants;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Java8 中的流库
 * 从迭代到流的操作
 * -------------------------- 流和集合的区别 --------------------------
 * 流并不储存任何元素,这些元素可能存在于底层的集合中或按需生成
 * 流的操作不会修改其数据源,而是生成一个新的流
 * 流的操作是惰性执行的
 *
 * @author zqw
 * @date 2022/6/16
 * @see Collection#stream()
 * @see Collection#parallelStream()
 */
public class Iterator2StreamTest {

    private static String contents;
    final Pattern pattern = Pattern.compile("\\PL+");

    static {
        try {
            contents = Files.readString(Paths.get("algorithm/set/a-tale-of-two-cities.txt"));
        } catch (IOException e) {
            contents = "";
        }
    }


    @Test
    public void stream() throws IOException {

        List<String> words = List.of(contents.split("\\PL+"));
        StopWatch stopWatch = new StopWatch();
        // ------------------------- general iteration -------------------------\
        stopWatch.start("general iteration");
        long counts = 0;
        for (String w : words) {
            if (w.length() > 12) {
                counts++;
            }
        }
        stopWatch.stop();
        System.out.println(counts);
        // ------------------------- common stream -------------------------
        stopWatch.start("common stream");
        counts = words.stream().filter(w -> w.length() > 12).count();
        stopWatch.stop();
        System.out.println(counts);
        // ------------------------- parallel stream -------------------------
        stopWatch.start("parallel stream");
        counts = words.parallelStream().filter(w -> w.length() > 12).count();
        stopWatch.stop();
        System.out.println(counts);
        System.out.println(stopWatch.prettyPrint());
    }

    @Test
    public void createStream() {
        // 可变长参数 \PL+: 以非字母分隔符
        String ret = "The world is difficult to joy, a scenery for the Milky Way.";
        Stream<String> stream = Stream.of(ret.split("\\PL+"));
        stream.forEach(Print::prints);
        int[] arr = {1, 2, 3, 4, 5};
        // [)
        Arrays.stream(arr, 2, 3).forEach(System.out::println);
        // 创建不包含任何元素的流
        Stream<String> silence = Stream.empty();
        silence.forEach(System.out::println);
    }

    @Test
    @SuppressWarnings("unused")
    public void unlimited() {
        /*创建无限流的方法*/
        // not to end!
        Stream<String> generate = Stream.generate(() -> "Echo");
        Stream<Double> randoms = Stream.generate(Math::random);
        Stream<BigInteger> integers = Stream.iterate(BigInteger.ZERO, n -> n.add(BigInteger.ONE));

        // 产生有限序列
        var limit = new BigInteger("10");
        // 碰到不满足 hasNext 谓词的元素时终止 谓词就是Predicate T -> boolean
        Stream<BigInteger> finite = Stream.iterate(BigInteger.ZERO, next -> next.compareTo(limit) < 0,
                n -> n.add(BigInteger.ONE));
        finite.forEach(Print::prints);
    }

    @Test
    public void splitString() throws IOException {
        String contents = "If you don't like where you are…,just picture where you wanna be.";
        // 将一个长的字符串分割为一个个的单词
        final Stream<String> words = pattern.splitAsStream(contents);
        words.forEach(Print::prints);
        System.out.println();
        Stream<String> symbolStream = new Scanner(contents).tokens();
        symbolStream.forEach(Print::prints);
        System.out.println();
        Path path = Paths.get(Constants.DEFAULT_FILE_PATH_MAME);
        // Paths::lines 会返回一个包含了文件中所有行的 Stream;{@link FileIoResolvingTest#releaseFileHandle}
        try (Stream<String> lines = Files.lines(path)) {
            lines.forEach(Print::println);
        }
    }

    @Test
    public void iterToStream() {
        // 如果 Iterable 对象不是集合; such as RandomStringGenerator
        List<String> list = List.of("iterator", "to", "stream");
        RandomStringGenerator<String> rsg = new RandomStringGenerator<>(list);
        // 可使用 StreamSupport.stream 将其转换为一个流
        Stream<String> stream = StreamSupport.stream(rsg.spliterator(), false);
        stream.forEach(Print::prints);

        // 如果是 Iterator 对象
        Iterator<String> iterator = rsg.iterator();
        Stream<String> iterStream = StreamSupport.stream(Spliterators.
                        spliteratorUnknownSize(iterator, Spliterator.ORDERED),
                false);
        iterStream.forEach(Print::prints);
    }

    @Test
    public void doNotReviseStreamCollection() {
        /*在执行流的操作时 最好不要修改流的底层集合*/
        // eg
        List<String> list = Tools.genString(5, 10);
        Stream<String> stream = list.stream();
        list.add("END");
        long count = stream.distinct().count();
        // result is 6, Well, normal work but not recommended!
        System.out.println(count);

        int max = 3;
        // java.lang.IllegalStateException:
        // stream has already been operated upon or closed
        stream.forEach(s -> {
            if (s.length() < max) {
                list.remove(s);
            }
        });
    }
}


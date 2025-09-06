package oak.stream;

import cn.hutool.core.collection.CollUtil;
import cn.qingweico.constants.Constants;
import cn.qingweico.constants.Symbol;
import cn.qingweico.io.Print;
import cn.qingweico.supplier.ObjectFactory;
import cn.qingweico.supplier.Tools;
import frame.db.JdbcConfig;
import object.entity.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testng.annotations.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static cn.qingweico.io.Print.println;

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

    public final List<Integer> container = Arrays.asList(9, 0, 1, 3, 5, 7, 8, 8);

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
        // Optional.ofNullable(null).orElse(Collections.emptyList());
    }

    @Test
    public void statefulStream() {
        container.stream().sorted().forEach(Print::prints);
        println();
        container.stream().limit(3).forEach(Print::prints);
        println();
        container.stream().skip(3).forEach(Print::prints);
        println();
        // The equals() and hashCode() methods need to be overridden when used on objects.
        container.stream().distinct().forEach(Print::prints);
    }

    @Test
    public void generate() {
        Stream.iterate(0, x -> x + 4).limit(5).forEach(Print::prints);
        println();
        // __ 占位符
        String payload = IntStream.rangeClosed(1, 10).mapToObj(String::valueOf)
                .collect(Collectors.joining(Symbol.DASHED)) + UUID.randomUUID();
        println(payload);
        Stream.generate(Math::random).limit(5).forEach(Print::prints);
        println();
        IntStream s = IntStream.iterate(0, x -> x + 4).limit(4);
        println(Arrays.toString(s.toArray()));
    }

    @Test
    public void statelessStream() {
        // map and reduce
        Optional<Integer> reduce = container.stream().map(x -> x * 2).filter(x -> x > 10).reduce(Math::min);
        reduce.ifPresent(System.out::println);
        container.stream().unordered().peek((e) -> Print.grace("peek", e)).forEach(System.out::println);
    }

    @Test
    public void shortCircuiting() {

        // Unordered, forEachOrdered() used with parallelStream() usually.
        container.parallelStream().forEach(Print::prints);
        println();
        // Ordered
        container.parallelStream().forEachOrdered(Print::prints);

        System.out.println(Arrays.toString(container.stream().map((x) -> x + 1).toArray(Integer[]::new)));
        long count = container.stream().map((x) -> x + 1).toList()
                .stream().max(Integer::compareTo)
                .stream().min(Integer::compareTo)
                .stream().count();

        System.out.println(count);
    }

    @Test
    public void unShortCircuiting() {
        boolean existNegative = container.stream().anyMatch((x) -> x < 0);
        boolean allPositive = container.stream().allMatch((x) -> x > 0);
        Print.grace("existNegative", existNegative);
        Print.grace("allPositive", allPositive);
        Optional<Integer> first = container.stream().findFirst();
        first.ifPresent(System.out::println);
        Optional<Integer> any = container.stream().findAny();
        any.ifPresent(System.out::println);
        boolean allNegative = container.stream().noneMatch((x) -> x >= 0);
        Print.grace("allNegative", allNegative);
    }

    @Test
    public void flatMap() {
        // General
        List<String> stringList = Arrays.asList("hello", "world");
        Stream<Stream<Character>> streamStream = stringList
                .stream()
                .map(OperateStreamTest::filterCharacter);

        streamStream.forEach((stream) -> stream.forEach(Print::prints));
        println();

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

    @Test
    public void statistics() {
        // 平均值
        Double avlVal = container.stream().collect(Collectors.averagingInt(e -> e));
        println(avlVal);
        // 数据统计信息
        IntSummaryStatistics intSummaryStatistics = container.stream().collect(Collectors.summarizingInt(e -> e));
        println(intSummaryStatistics);
    }

    @Test
    public void group() {
        // 分组
        Map<Short, List<Integer>> listMap = container.stream().collect(Collectors.groupingBy(Integer::shortValue));
        Print.printMap(listMap);
    }

    /**
     * Stream 中 map 和 peek 的区别
     */
    @Test
    public void mapAndPeekDiff() {
        List<User> list = Collections.singletonList(ObjectFactory.create(User.class, true));
        Print.printColl(list);
        Print.printColl(list.stream().map(User::getId).collect(Collectors.toList()));
        Print.printColl(list.stream().peek(e -> e.setId(1L)).collect(Collectors.toList()));
    }

    /**
     * CREATE TABLE unq_idx_table (
     *     id INT AUTO_INCREMENT PRIMARY KEY,
     *     field1 VARCHAR(50),
     *     field2 VARCHAR(50),
     *     field3 VARCHAR(50),
     *     field4 VARCHAR(50),
     *     UNIQUE INDEX unique_index_field1_field2 (field1, field2),
     *     UNIQUE INDEX unique_index_field3 (field3),
     *     UNIQUE INDEX unique_index_field4 (field4)
     * );
     */
    @Test
    public void groupingBy() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JdbcConfig.class);
        JdbcTemplate jdbcTemplate = context.getBean(JdbcTemplate.class);
        String queryTableUnqIdx = "SHOW INDEX FROM ${Table_Name} WHERE Non_unique = 0";
        List<Map<String, Object>> result;
        String tableName = "unq_idx_table";
        result = jdbcTemplate.queryForList(queryTableUnqIdx.replace("${Table_Name}", tableName));
        if (CollUtil.isNotEmpty(result)) {
            Map<String, List<String>> uniqueIndexMap = result.stream()
                    .filter(map -> !Constants.PRIMARY.equals(map.get("Key_name")))
                    // classifier : 接受流中的每个元素作为输入, 返回一个用于分组的键
                    // downstream 下游收集器 : 指定了对每个分组中的元素的搜集策略
                    // 根据map中Key_name列的元素分组
                    .collect(Collectors.groupingBy(map -> (String) map.get("Key_name"),
                            // 搜集map中Column_name列的元素为List
                            Collectors.mapping(map -> (String) map.get("Column_name"),
                                    Collectors.toList())));
            Print.printMap(uniqueIndexMap);
        }
    }

    @Test
    public void doubleSummaryStatistics() {
        double[] data = Tools.genDoubleArray(10, 100);
        Print.print(data);
        DoubleSummaryStatistics statistics = Arrays.stream(data)
                .summaryStatistics();
        Print.grace("数量", statistics.getCount());
        Print.grace("总和", statistics.getSum());
        Print.grace("最小值", statistics.getMin());
        Print.grace("最大值", statistics.getMax());
        Print.grace("平均值", statistics.getAverage());
    }
}

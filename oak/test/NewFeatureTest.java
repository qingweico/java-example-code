package oak.test;


import org.testng.annotations.Test;

import java.io.*;
import java.time.*;
import java.util.*;
import java.util.function.BiFunction;

/**
 * The new feature after jdk8 and jdk8
 *
 * @author zqw
 * @date 2022/10/22
 */
public class NewFeatureTest {
    @Test
    public void localDate() {
        var date = LocalDate.of(2021, 10, 22);
        var time = LocalTime.of(19, 36);
        var dateTime = LocalDateTime.of(date, time);
        System.out.println(dateTime);
        var zoneDt = ZonedDateTime.of(dateTime, ZoneId.of("Europe/Paris"));
        System.out.println(zoneDt);
    }

    @Test
    public void base64() {
        Base64.Decoder decode = Base64.getDecoder();
        Base64.Encoder encoder = Base64.getEncoder();
        String encoderString = encoder.encodeToString(new byte[]{'b', 'a', 's', 'e', '6', '4'});
        System.out.println(encoderString);
        String decodeString = new String(decode.decode(encoderString));
        System.out.println(decodeString);
    }

    /**
     * jdk9
     *
     * @see Collections#unmodifiableSet
     * @see Collections#unmodifiableMap
     * @see Collections#unmodifiableList
     * @see Arrays#asList
     * ;
     */
    @Test
    public void jdk9() throws FileNotFoundException {
        // of静态工厂方法创建实例(因为相当一部分集合实例都是容量有限的,而且在生命周期中并不会修改)
        // 使用特定参数长度而不是使用可变长参数 因为
        // JVM 在处理变长参数的时候会有明显的额外开销
        Set<Integer> set = Set.of(1, 2, 3);
        System.out.println(set);

        // 接口可以添加私有方法

        // 改进try-with-resources; 不需用在try中额外定义变量
        InputStream is = new BufferedInputStream(new FileInputStream(""));
        try (is) {
            is.readAllBytes();
        } catch (IOException e) {
            /*ignore*/
        }
        // 多版本兼容jar包

        // 匿名内部类钻石操作符的增强(可以在内部类中使用)
        new ArrayList<>() {

        };


    }

    @Test
    public void jdk10() {
        // 局部变量的自动类型推断 var
        var str = "str";
        System.out.println(str);
    }

    /**
     * jdk11
     */
    @Test
    public void jdk11() {
        // Lambda中使用var
        BiFunction<String, String, String> fn = (var x, var y) -> x + y;
        String apply = fn.apply("lam", "bda");
        System.out.println(apply);
        // 字符串增强
        // isEmpty()
        System.out.println(" ".isBlank());

        System.out.println("jdk11  ".stripTrailing());
        System.out.println(" jdk11".stripLeading());
        // java + javac 命令合一
    }
    // jdk12: switch使用lambda; instanceof类型强转(obj instanceof String str);
}
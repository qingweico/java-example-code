package oak.lambda;

import util.Print;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static util.Print.println;
import static util.Print.print;

/**
 * @author zqw
 * @date 2021/2/24
 */
@SuppressWarnings("all")
public class Foreach {
    public static void main(String[] args) {
        List<String> strings = Arrays.asList("you", "and", "me");
        strings.forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                print(s);
            }
        });
        println();
        strings.forEach((s) -> print(s));
        println();
        strings.forEach(Print::print);
    }
}

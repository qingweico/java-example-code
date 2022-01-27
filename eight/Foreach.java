package eight;

import util.Print;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static util.Print.print;
import static util.Print.printnb;

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
                printnb(s);
            }
        });
        print();
        strings.forEach((s) -> printnb(s));
        print();
        strings.forEach(Print::printnb);
    }
}

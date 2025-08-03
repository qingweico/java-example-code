package oak.lambda;

import cn.qingweico.io.Print;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;


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
                Print.print(s);
            }
        });
        Print.println();
        strings.forEach((s) -> Print.print(s));
        Print.println();
        strings.forEach(Print::print);
    }
}

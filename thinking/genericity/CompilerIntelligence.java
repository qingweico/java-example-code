package thinking.genericity;

import java.util.Arrays;
import java.util.List;

/**
 * @author:qiming
 * @date: 2021/1/12
 */
public class CompilerIntelligence {
    public static void main(String[] args) {
        List<? extends Fruit> list = Arrays.asList(new Orange());
        Apple a = (Apple) list.get(0);
        list.contains(new Apple());
        list.indexOf(new Apple());

    }
}

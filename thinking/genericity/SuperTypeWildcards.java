package thinking.genericity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author:qiming
 * @date: 2021/1/12
 */
public class SuperTypeWildcards {
    static void writeTo(List<? super Apple> apples) {
        apples.add(new Apple());
        apples.add(new Jonathan());

        // Error
        // apples.add(new Fruit());
    }

    public static void main(String[] args) {
        List<? super Apple> apples = new ArrayList<Fruit>();
        writeTo(apples);
        Fruit apple = (Apple) apples.get(0);
        System.out.println(apple);
    }
}

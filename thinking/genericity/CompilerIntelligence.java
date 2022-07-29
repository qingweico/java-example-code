package thinking.genericity;

import java.util.List;

/**
 * @author zqw
 * @date 2021/1/12
 */
class CompilerIntelligence {
    public static void main(String[] args) {
        List<? extends Fruit> list = List.of(new Orange());
        Apple notApple = (Apple) list.get(0);
        System.out.println(notApple);
        System.out.println(list.contains(new Apple()));
        System.out.println(list.indexOf(new Apple()));
    }
}

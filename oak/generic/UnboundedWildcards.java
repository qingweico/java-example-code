package oak.generic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * 无界通配符(上下界) <?> <? extends T> <? super T>
 * @author zqw
 * @date 2023/6/24
 * The example of this in jdk{@link java.util.Collections#copy(List, List)}
 */
class UnboundedWildcards {
    public static void main(String[] args) {

        // List<? extends T>  只能读取 不能插入

        // ? extends Number 数据为Number类型或者Number的子类型
        // 所以List到底是什么具体类型并不知道, 所以防止容器中出现两种类型的数据
        // 编译时期便会报错阻止这种情况发生
        List<? extends Number> upperBound = new ArrayList<>(Arrays.asList(2.5, 0.5, -9.5));
        for(Number e : upperBound) {
            System.out.println(e);
        }

        // List<? super T> 只能插入 以及只能用 Object 类型读取
        List<? super Number> lowerBound = new ArrayList<>();
        lowerBound.add(2.4d);
        lowerBound.add(-.6f);
        lowerBound.add(2);
        for(Object e : lowerBound) {
            System.out.println(e);
        }

        // PECS原则 @see effective.Article7
    }
}

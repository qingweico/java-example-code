package collection;

import cn.qingweico.supplier.Tools;
import oak.stream.OperateStreamTest;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author zqw
 * @date 2025/9/9
 */
public class SpottedCollectionTest {


    /**
     * Collection 中 remove接口问题(继承 + 重载(多态))
     * 原因为List 接口和 Collection 接口都定义了一个 remove 方法
     * 但是其入参不同, 前者为元素索引, 后者为要移除的指定元素
     * list 的编译时类型是List<Integer>, 而 coll 的编译时类型是 Collection<Integer>
     * 由于Collection 接口没有 remove(int index) 方法,只有 remove(Object o) 方法,
     * 因此参数会自动装箱, 从而调用remove(Object o)方法
     * resolve: 类型转换或者使用迭代器
     */

    @Test
    public void remove() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        // List 接口中的remove(int index) 方法
        list.remove(1);
        // [1, 3]
        System.out.println(list);

        Collection<Integer> coll = new ArrayList<>();
        coll.add(1);
        coll.add(2);
        coll.add(3);
        // Collection 接口中的remove(Object o) 方法
        coll.remove(1);
        // [2, 3]
        System.out.println(coll);
    }

    @Test
    public void varCompileType() {
        // 使用 var 之前, 明确希望接受的类型(IDE自动推断的类型可能不是期望的类型)
        // 希望 list 编译时的类型是 Collection, 但是自动推断出来的类型却是ArrayList
        // 如果再调用remove方法就会出现上面的问题
        var list = new ArrayList<>();
        // populate
        System.out.println(list);
    }

    /**
     * {@link Arrays#asList(Object[])} 并不是一个真正的不可变集合
     *
     * @since JDK9, 请使用 {@link List#of()} 创建不可变 List
     * May your code always be robust and predictable
     */
    @Test
    public void notRealImmutable() {
        List<String> list = Arrays.asList("1", "2", "3");
        // 可以修改成功
        list.set(0, "4");
        System.out.println(list);
        // UnsupportedOperationException
        list.add("4");
        System.out.println(list);
    }

    /**
     * 纯函数: 不修改外部状态;不依赖外部可变状态
     */
    @Test
    public void pureFunction() {
        List<String> ori = Tools.genString(5, 4);
        // lambda 必须是纯函数, 无副作用
        // ori.parallelStream().map(String::toUpperCase).forEach(upper::add);
        List<String> upper = ori.parallelStream().map(String::toUpperCase).toList();
        System.out.println(ori.size());
        System.out.println(upper.size());
    }

    /**
     * Stream API 依靠惰性求值提高效率, 而惰性求值又依靠纯函数保证正确性
     * @see OperateStreamTest
     */
    @Test
    public void lazyEvaluation(){
        List<Integer> list = Arrays.asList(1, 2, 3);
        int[] factor = {2};
        Stream<Integer> stream = list.stream().map(x -> x * factor[0]);
        // 修改外部状态
        factor[0] = 0;
        System.out.println(stream.toList());
    }
}

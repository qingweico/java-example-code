package geek.collection;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

/**
 * List列表操作应该避免的问题
 *
 * @author zqw
 * @date 2022/8/2
 */
@Slf4j
class OptList {
    public static void main(String[] args) {
        Integer[] integers = {1, 2, 3};
        List<Integer> integerList = Arrays.asList(integers);
        log.info("list:{} size:{} class:{}", integerList, integerList.size(), integerList.get(0).getClass());
        int[] intArray = {1, 2, 3};
        // 泛型参数T: 可以将int包装为Integer 但是无法将int数组装箱为Integer数组 最终将int[] 整体作为泛型参数传入
        List<int[]> intList = List.of(intArray);
        log.info("list:{} size:{} class:{}", intList, intList.size(), intList.get(0).getClass());

        // 1: 不可以使用 Arrays.asList() 转换基本类型数组
        String[] strings = {"here", "we", "go"};
        List<String> list = Arrays.asList(strings);
        strings[1] = "you";
        // changed!
        // 共享数组
        /*solved: new ArrayList(Arrays.asList());*/
        System.out.println(list);
        // java.util.Arrays.ArrayList.ArrayList extends java.util.AbstractList#add
        // 子类没有覆盖父类中的add方法,会抛出UnsupportedException
        // list.add("!");
    }
}

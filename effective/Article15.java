package effective;

import java.util.List;

/**
 * 使类和成员的可访问性最小化
 *
 * @author:qiming
 * @date: 2021/11/21
 */
public class Article15 {
    // 设计良好的组件会隐藏所有的实现细节, 把API和实现清晰地隔离开来,
    // 然后组件之间只通过API进行通讯, 一个模块不需要知道其他模块的内部工作状况
    // 这个概念被称为信息隐藏或者封装

    // Arrays of non-zero length are always mutable
    // Potential security hole!
    public static final Object[] VALUES = {};


    private static final Object[] PRIVATE_VALUES = {};

    public static final List<Object> unmodifiableVALUES = List.of(PRIVATE_VALUES);

    public static Object[] values () {
        return PRIVATE_VALUES.clone();
    }

}

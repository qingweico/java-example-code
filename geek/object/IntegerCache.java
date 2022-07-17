package geek.object;

import java.lang.reflect.Field;
import java.util.IdentityHashMap;
import java.util.Map;

/**
 * 从 Java5 开始构建Integer对象的传统方式是直接调用构造器, 直接new一个对象
 * 但是大部分数据操作都是集中在有限的,较小的数值范围 {@since JDK5} 新增了静态工厂方法
 * {@link Integer#valueOf(int)} 这个值默认缓存是-128 ~ 127
 * 可以通过 VM 参数 {@code -XX:AutoBoxCacheMax=N} 调整缓存上限值 {@link Integer.IntegerCache#static}
 * 自动装箱和自动拆箱(packing and unpacking) 实际上是一种语法糖
 * 语法糖: Java平台会自动进行一些转化,保证不同的写法在运行时等价,它们发生在编译阶段,也就是生成的字节码是一致的
 * packing {@link Integer#valueOf(int)}
 * unpacking {@link Integer#intValue()}
 *
 * @author zqw
 * @date 2022/7/2
 */
@SuppressWarnings("all")
class IntegerCache {
    public static void main(String[] args) {
        // true
        // -XX:AutoBoxCacheMax=255
        // -Djava.lang.Integer.IntegerCache.high=255
        Integer i = 128;
        Integer j = 128;
        System.out.println(i == j);

        Integer a = Integer.parseInt("10");
        Integer b = Integer.valueOf("10");
        Integer c = 10;
        changedValues(a, 100);
        changedValues(b, 100);
        changedValues(c, 100);
        // 100 100 100
        System.out.printf("a = %d b = %d c = %d%n", a, b, c);

        Map<Integer, String> map = new IdentityHashMap<>();
        map.put(1, "1");
        map.putIfAbsent(1, "01");
        // 1 IdentityHashMap 比较key使用 ==, 由于 1
        // 在Integer Cache范围内
        // 所以第二个1不会put成功
        System.out.println(map.get(1));
        // 1
        System.out.println(map.size());

        map.put(1024, "A");
        map.putIfAbsent(1024, "B");
        // null
        // 1024 不在 Integer Cache 范围内
        // 所以 第二个1024会put成功
        // get 时同样使用 == 判断key是否相同
        System.out.println(map.get(1024));
        // 3
        System.out.println(map.size());

    }

    public static void changedValues(Integer i, int value) {
        try {
            Field field = Integer.class.getDeclaredField("value");
            field.setAccessible(true);
            field.set(i, value);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

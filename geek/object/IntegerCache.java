package geek.object;

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
public class IntegerCache {
    public static void main(String[] args) {
        // true
        // -XX:AutoBoxCacheMax=255
        // -Djava.lang.Integer.IntegerCache.high=255
        Integer i = 128;
        Integer j = 128;
        System.out.println(i == j);
    }
}

package thread.cas;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author zqw
 * @date 2022/2/7
 */
public class UnsafeSupport {
    /**
     * 或者使用虚拟机参数-Xbootclasspath/a: ${path} 将调用Unsafe方法的类加入到系统类加载路径上
     *
     * @return {@link Unsafe}
     */
    public static Unsafe reflectGetUnsafe() {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            return (Unsafe) field.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return null;
        }
    }
}

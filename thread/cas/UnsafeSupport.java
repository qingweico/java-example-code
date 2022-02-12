package thread.cas;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * cmpxchg implement cas see {@code openjdk}
 * {@code unsafe.cpp#UNSAFE_ENTRY(Unsafe_CompareAndSwapInt)}
 * {@code atomic_linux_x86.inline.hpp#Atomic::cmpxchg; LOCK_IF_MP(mp)}
 * {@code os.hpp#is_MP()}
 *
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

    public static void shortWait(long interval) {
        long start = System.nanoTime();
        long end;
        do {
            end = System.nanoTime();
        } while (start + interval >= end);
    }
}

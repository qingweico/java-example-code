package thread.cas;

import sun.misc.Unsafe;
import util.process.ProcessExecutor;

import java.lang.reflect.Field;

/**
 * cmpxchg implement cas see {@code openjdk}
 * {@code unsafe.cpp#UNSAFE_ENTRY(Unsafe_CompareAndSwapInt)}
 * {@code atomic_linux_x86.inline.hpp#Atomic::cmpxchg; LOCK_IF_MP(mp)}
 * {@code os.hpp#is_MP()}
 * 不使用反射拿到 {@link Unsafe}, 显示使用CAS, 请参考{@link java.lang.invoke.VarHandle}
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

    /**
     *
     * @param interval the waiting interval;  unit: ms
     * {@link ProcessExecutor#waitFor(long)}
     */
    public static void shortWait(long interval) {
        long start = System.currentTimeMillis();
        long end;
        do {
            end = System.currentTimeMillis();
        } while (start + interval >= end);
    }
}

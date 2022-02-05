package thread.cas;

import org.testng.annotations.Test;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

import static util.Print.err;
import static util.Print.print;

/**
 * @author zqw
 * @date 2021/3/5
 */
public class UnsafeExampleTest {

    int i = 0;
    private static final UnsafeExampleTest U = new UnsafeExampleTest();

    @Test
    public void cas() throws NoSuchFieldException {
        Unsafe unsafe = reflectGetUnsafe();
        if (unsafe == null) {
            return;
        }
        Field f = UnsafeExampleTest.class.getDeclaredField("i");
        long offset = unsafe.objectFieldOffset(f);

        print(offset);

        boolean success = unsafe.compareAndSwapInt(U, offset, 0, 1);
        print(success);
        print(U.i);

    }

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
            err(e.getMessage());
            return null;
        }
    }

    @Test
    public void testUnsafe() throws NoSuchFieldException {
        Unsafe unsafe = reflectGetUnsafe();
        if (unsafe == null) {
            return;
        }
        // 返回系统指针的大小 32机器为4; 64位机器为8
        print(unsafe.addressSize());
        // 内存页的大小 此值为2的幂次方
        print(unsafe.pageSize());
    }
}

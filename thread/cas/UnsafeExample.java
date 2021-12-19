package thread.cas;

import org.junit.Test;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

import static util.Print.err;
import static util.Print.print;

/**
 * @author:qiming
 * @date: 2021/3/5
 */
public class UnsafeExample {

    int i = 0;
    private static final UnsafeExample u = new UnsafeExample();

    public static void main(String[] args) throws Exception {

        Field unsafeField = Unsafe.class.getDeclaredFields()[0];
        unsafeField.setAccessible(true);
        Unsafe unsafe = (Unsafe) unsafeField.get(null);

        Field f = UnsafeExample.class.getDeclaredField("i");
        long offset = unsafe.objectFieldOffset(f);

        print(offset);

        boolean success = unsafe.compareAndSwapInt(u, offset, 0, 1);
        print(success);
        print(u.i);
    }
    // 或者使用虚拟机参数-Xbootclasspath/a: ${path} 将调用Unsafe方法的类加入到系统类加载路径上
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
        if (unsafe == null) return;
        // 返回系统指针的大小 32机器为4; 64位机器为8
        print(unsafe.addressSize());
        // 内存页的大小 此值为2的幂次方
        print(unsafe.pageSize());
    }
}

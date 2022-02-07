package thread.cas;

import org.testng.annotations.Test;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

import static util.Print.print;

/**
 * @author zqw
 * @date 2021/3/5
 */
public class UnsafeTest {

    int i = 0;
    private static final UnsafeTest U = new UnsafeTest();

    @Test
    public void cas() throws NoSuchFieldException {
        Unsafe unsafe = UnsafeSupport.reflectGetUnsafe();
        if (unsafe == null) {
            throw new RuntimeException("unsafe is null");
        }
        Field f = UnsafeTest.class.getDeclaredField("i");
        long offset = unsafe.objectFieldOffset(f);

        print(offset);

        boolean success = unsafe.compareAndSwapInt(U, offset, 0, 1);
        print(success);
        print(U.i);

    }


    @Test
    public void testUnsafe() throws NoSuchFieldException {
        Unsafe unsafe = UnsafeSupport.reflectGetUnsafe();
        if (unsafe == null) {
            throw new RuntimeException("unsafe is null");
        }
        // 返回系统指针的大小 32机器为4; 64位机器为8
        print(unsafe.addressSize());
        // 内存页的大小 此值为2的幂次方
        print(unsafe.pageSize());
    }
}

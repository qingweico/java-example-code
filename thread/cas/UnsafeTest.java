package thread.cas;

import oak.User;
import org.testng.annotations.Test;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

import static util.Print.print;

/**
 * @author zqw
 * @date 2021/3/5
 */
public class UnsafeTest {
    private static Unsafe unsafe;

    static {
        unsafe = UnsafeSupport.reflectGetUnsafe();
        if (unsafe == null) {
            throw new RuntimeException("unsafe is null");
        }
    }

    int i = 0;
    Integer j = 10;
    private static final UnsafeTest U = new UnsafeTest();

    @Test
    public void cas() throws NoSuchFieldException {
        Field f = UnsafeTest.class.getDeclaredField("i");
        long offset = unsafe.objectFieldOffset(f);

        print(offset);

        boolean success = unsafe.compareAndSwapInt(U, offset, 0, 1);
        print(success);
        print(U.i);

    }

    @Test
    public void objectAcquire() {
        // unsafe.getObjectVolatile()
    }

    @Test
    public void createObjectVol() throws InstantiationException {
        Object instance = unsafe.allocateInstance(User.class);
        User user = (User) instance;
        // allocateInstance 方法并不会初始化实例字段;而new语句和反射机制
        // 则是通过调用构造器来初始化实例字段
        System.out.println(user.getInstantiation());
    }


    @Test
    public void testUnsafe() throws NoSuchFieldException {
        // 返回系统指针的大小 32机器为4; 64位机器为8
        print(unsafe.addressSize());
        // 内存页的大小 此值为2的幂次方
        print(unsafe.pageSize());
    }
}

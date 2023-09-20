package thread.cas;

import object.entity.User;
import org.junit.Test;
import sun.misc.Unsafe;
import util.Print;

import java.lang.reflect.Field;

/**
 * @author zqw
 * @date 2021/3/5
 */
public class UnsafeTest {
    private static final Unsafe U = UnsafeSupport.reflectGetUnsafe();

    static {
        if (U == null) {
            throw new RuntimeException("unsafe is null");
        }
    }

    int i = 0;
    private static final UnsafeTest INSTANCE = new UnsafeTest();

    @Test
    public void cas() throws NoSuchFieldException {
        Field f = UnsafeTest.class.getDeclaredField("i");
        long offset = U.objectFieldOffset(f);

        Print.println(offset);

        boolean success = U.compareAndSwapInt(INSTANCE, offset, 0, 1);
        Print.println(success);
        Print.println(INSTANCE.i);

    }

    @Test
    public void objectAcquire() {
        // unsafe.getObjectVolatile();
    }

    @Test
    public void createObjectVol() throws InstantiationException {
        Object instance = U.allocateInstance(User.class);
        User user = (User) instance;
        // allocateInstance 方法并不会初始化实例字段;而new语句和反射机制
        // 则是通过调用构造器来初始化实例字段
        System.out.println(user.getInstantiation());
    }


    @Test
    public void testUnsafe() {
        // 返回系统指针的大小 32机器为4; 64位机器为8
        Print.println(U.addressSize());
        // 内存页的大小 此值为2的幂次方
        Print.println(U.pageSize());
    }
}

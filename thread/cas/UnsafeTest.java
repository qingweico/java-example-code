package thread.cas;

import cn.qingweico.concurrent.UnsafeSupport;
import cn.qingweico.concurrent.pool.ThreadPoolBuilder;
import cn.qingweico.io.Print;
import cn.qingweico.supplier.RandomDataGenerator;
import lombok.Getter;
import lombok.Setter;
import object.entity.User;
import org.junit.Test;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.ExecutorService;

/**
 * Unsafe的使用
 * @author zqw
 * @date 2021/3/5
 */
public class UnsafeTest {
    private static final Unsafe U = UnsafeSupport.reflectGetUnsafe();

    ExecutorService pool = ThreadPoolBuilder.builder().build();

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

    // 类、对象和变量相关方法

    @Test
    public void getSetObject() throws Exception {
        User user = new User();
        user.setUsername(RandomDataGenerator.name());
        Field username = User.class.getDeclaredField("username");
        // 返回 非静态属性 在其对象存储分配中的位置(偏移地址), 对静态属性使用会抛异常
        // 其方法staticFieldOffset与其相反
        long offset = U.objectFieldOffset(username);
        // 根据 对象中的偏移地址 offset 来获取值 突破修饰符的限制
        Object value = U.getObject(user, offset);
        System.out.println(value);
        // 根据 对象中的偏移地址 offset 来设置值 突破修饰符的限制
        U.putObject(user, offset, "Unsafe putObject");
        System.out.println(user.getUsername());
    }

    @Test
    public void getSetVolatileObject() throws Exception {
        // # getObjectVolatile
        // # putObjectVolatile
        @Setter
        @Getter
        class O {
            Integer value;
        }
        O o = new O();
        o.setValue(1);
        pool.execute(() -> {
            for (; ; ) {
                if (o.getValue() != 1) {
                    break;
                }
            }
            System.out.println("o.getValue() == 2");
        });
        UnsafeSupport.shortWait(100);
        pool.execute(() -> {
            o.setValue(2);
        });
        System.out.println(System.in.read());
    }
}

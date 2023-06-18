package oak.base;

import lombok.extern.slf4j.Slf4j;
import object.entity.User;
import org.junit.Test;
import util.ObjectFactory;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;

/**
 * 创建对象的几种方式
 *
 * @author zqw
 * @date 2023/6/17
 */
@Slf4j
public class ObjectCreatingTest {

    // new

    // clone

    // 反射

    // 序列化(序列化和反序列化生成的对象不是同一个)

    // Unsafe(@see UnsafeTest#createObjectVol)

    // MethodHandler

    @Test
    public void reflect() {
         try {
             Constructor<User> constructor = User.class.getDeclaredConstructor(Long.class, String.class, boolean.class);
             User user = constructor.newInstance(1L, "User", true);
             System.out.println(user);
         }catch (Exception ex) {
             log.error("reflect error");
         }

    }

    @Test
    public void cloneObject() {
       User user = ObjectFactory.create(User.class, true);
       // 不会调用构造方法
       User clonedUser = user.clone();
       System.out.println(user);
       System.out.println(clonedUser);
    }

    @Test
    public void methodHandlers() {
        try {
            MethodHandle constructor = MethodHandles.lookup().findConstructor(User.class, MethodType.methodType(void.class, Long.class, String.class, boolean.class));
            System.out.println(constructor.invoke(1L, "User", true));
        }catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}

package thread.cas;

import java.lang.reflect.Field;

import static util.Print.print;

/**
 * @author:qiming
 * @date: 2021/3/5
 */
public class Unsafe {

    int i = 0;
    private static final Unsafe t = new Unsafe();

    public static void main(String[] args) throws Exception {

        Field unsafeField = sun.misc.Unsafe.class.getDeclaredFields()[0];
        unsafeField.setAccessible(true);
        sun.misc.Unsafe unsafe = (sun.misc.Unsafe) unsafeField.get(null);

        Field f = Unsafe.class.getDeclaredField("i");
        long offset = unsafe.objectFieldOffset(f);

        print(offset);

        boolean success = unsafe.compareAndSwapInt(t, offset, 0, 1);
        print(success);
        print(t.i);
    }
}

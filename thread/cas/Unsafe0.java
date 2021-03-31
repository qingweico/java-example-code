package thread.cas;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

import static util.Print.print;

/**
 * @author:qiming
 * @date: 2021/3/5
 */
public class Unsafe0 {

    int i = 0;
    private static final Unsafe0 t = new Unsafe0();

    public static void main(String[] args) throws Exception {
        // Unsafe unsafe1 = Unsafe.getUnsafe();


        Field unsafeField = Unsafe.class.getDeclaredFields()[0];
        unsafeField.setAccessible(true);
        Unsafe unsafe = (Unsafe) unsafeField.get(null);

        Field f = Unsafe0.class.getDeclaredField("i");
        long offset = unsafe.objectFieldOffset(f);

        print(offset);

        boolean success = unsafe.compareAndSwapInt(t, offset, 0, 1);
        print(success);
        print(t.i);
    }
}

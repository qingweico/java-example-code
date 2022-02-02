package jvm;

import sun.misc.Unsafe;
import util.Constants;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;

/**
 * @author zqw
 * @date 2021/2/20
 */
public class DirectMemory {
    private static final int BUFFER = Constants.KB * Constants.KB * Constants.TEN;

    public static void main(String[] args) throws IOException, IllegalAccessException {
        ByteBuffer buffer = ByteBuffer.allocateDirect(BUFFER);
        // Blocking
        buffer.put((byte) System.in.read());

        buffer = null;
        // Release memory
        System.gc();

        // Using reflection to create an UnSafe class bypasses using DirectByteBuffer.
        Field field = Unsafe.class.getDeclaredFields()[0];
        field.setAccessible(true);
        Unsafe unsafe = (Unsafe) field.get(null);
        for (; ; ) {
            try {
                unsafe.allocateMemory(BUFFER);
            }catch (Throwable t) {
                break;
            }
        }
    }
}

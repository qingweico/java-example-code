package jvm;

import sun.misc.Unsafe;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;

/**
 * @author:qiming
 * @date: 2021/2/20
 */
public class DirectMemory {
    private static final int BUFFER = 1024 * 1024 * 1024;
    public static void main(String[] args) throws IOException, IllegalAccessException {
        ByteBuffer buffer = ByteBuffer.allocateDirect(BUFFER);
        // Blocking
        int n = System.in.read();
        buffer = null;
        // Release memory
        System.gc();

        // Using reflection to create an UnSafe class bypasses using DirectByteBuffer.
        Field field = Unsafe.class.getDeclaredFields()[0];
        field.setAccessible(true);
        Unsafe unsafe = (Unsafe)field.get(null);
        for(;;) {
            unsafe.allocateMemory(500 * 1024 * 1024);
        }
    }
}

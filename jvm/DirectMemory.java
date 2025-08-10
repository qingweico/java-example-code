package jvm;

import sun.misc.Unsafe;
import cn.qingweico.constants.Constants;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;

/**
 * VM args: {@code -XX:PageAlignDirectMemory=true}
 * 用于控制直接内存(Direct Memory)的分配方式, 为true时
 * JVM 会强制按操作系统内存页(Page)对齐, 分配直接内存
 *
 * @author zqw
 * @date 2021/2/20
 * @see ByteBuffer#allocateDirect
 * @see Unsafe#allocateMemory
 * 默认方式为false, JVM 可能会按较小的对齐方式分配直接内存, 以提高分配速度
 * 需要开启的场景
 * - 使用 JNI 调用 或 硬件加速
 * - 使用 内存映射文件(mmap) 或 零拷贝(sendfile)
 * - 需要 减少内存碎片 的场景
 */
class DirectMemory {
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
            } catch (Throwable t) {
                break;
            }
        }
    }
}

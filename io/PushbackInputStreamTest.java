package io;

import java.io.*;

/**
 * {@link PushbackInputStream} 包装原始输入流, 内部使用一个 buffer 缓冲数组可以用来回退操作(默认大小为1,
 * 只能回退这部分大小的字节流), 以便可以重复读取某部分流; 使用 read 读取数据时, 先从缓冲区中读取, 如果缓冲区
 * 中没有数据(通过pos游标判断), 则从原始输入流中读取数据; 当使用 unread 方法时, 会将pos退回到buf数组下标的
 * 某个位置, 并将参数的字节数组复制到内部的buf数组中, 在下次使用read方法时就会优先读取
 * @see PushbackReader
 * @author zqw
 * @date 2025/10/13
 */
public class PushbackInputStreamTest {
    public static void main(String[] args) throws IOException {
        byte[] original = "PushbackInputStream Original".getBytes();

        int pushbackBufferSize = original.length;
        InputStream inputStream = new ByteArrayInputStream(original);
        PushbackInputStream pushbackInputStream = new PushbackInputStream(inputStream, pushbackBufferSize);

        try (ByteArrayOutputStream out1 = new ByteArrayOutputStream(pushbackBufferSize)) {
            int len;
            int read = 0;
            byte[] buffer = new byte[1];
            byte[] readBuffer = new byte[pushbackBufferSize];
            while ((len = pushbackInputStream.read(buffer)) != -1) {
                out1.write(buffer, 0, len);
                System.arraycopy(buffer, 0, readBuffer, read, len);
                read += len;
            }
            pushbackInputStream.unread(readBuffer, 0, read);
            System.out.println(out1);
        }


        try (ByteArrayOutputStream out2 = new ByteArrayOutputStream(pushbackBufferSize)) {
            int len;
            byte[] buffer = new byte[2];
            while ((len = pushbackInputStream.read(buffer)) != -1) {
                out2.write(buffer, 0, len);
            }
            System.out.println(out2);
        }
    }
}

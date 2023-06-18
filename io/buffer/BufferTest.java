package io.buffer;

import org.junit.Test;
import util.constants.Constants;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.IntBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author zqw
 * @date 2021/9/27
 */
public class BufferTest {

    final String fileName = Constants.DEFAULT_FILE_PATH_MAME;

    static int neededNewline = 20;
    private final ByteBuffer buffer = ByteBuffer.allocate(1024 * 8);

    @Test
    public void gen() throws IOException {
        Random r = new Random();
        var os = new BufferedOutputStream(new FileOutputStream(fileName));
        var t = System.currentTimeMillis();
        for (int i = 0; i < Constants.NUM_1000; i++) {
            for (int j = 0; j < Constants.FIVE; j++) {
                os.write(97 + r.nextInt(5));
            }
            os.write(' ');
            if (i != 0 && i % neededNewline == 0) {
                os.write("\n".getBytes());
            }
        }
        System.out.format("%dms", (System.currentTimeMillis() - t));
        os.close();
    }

    @Test
    public void read() throws IOException {
        var is = new BufferedInputStream(new FileInputStream(fileName));
        int count = 50;
        var start = System.nanoTime();
        byte[] bytes = new byte[24];
        while ((is.read(bytes)) != -1) {
            System.out.print('.');
            count--;
            if (count < 0) {
                System.out.println();
                count = 50;
            }
        }
        System.out.format("%fms", (System.nanoTime() - start) / 1000_000.0);
        is.close();
    }

    @Test
    public void readNio() throws IOException {
        try (FileInputStream fis = new FileInputStream(fileName)) {
            var fileChannel = fis.getChannel();
            var start = System.nanoTime();
            int readBytes;
            while ((readBytes = fileChannel.read(buffer)) != -1) {
                System.out.print(new String(buffer.array(), 0, readBytes));
                buffer.flip();
                buffer.clear();
            }
            System.out.format("%fms", (System.nanoTime() - start) / 1000_000.0);
            fileChannel.close();
        }
    }

    @Test
    public void writeNio() throws IOException {
        try (FileOutputStream fos = new FileOutputStream(fileName, true)) {
            var fileChannel = fos.getChannel();
            var start = System.nanoTime();
            String s = "AsYouWish";
            buffer.put(s.getBytes());
            buffer.flip();
            fileChannel.write(buffer);
            System.out.format("%fms", (System.nanoTime() - start) / 1000_000.0);
        }
    }

    @Test
    public void readAsync() throws IOException, ExecutionException, InterruptedException {
        var channel = AsynchronousFileChannel.open(Path.of(fileName), StandardOpenOption.READ);
        var buffer = ByteBuffer.allocate(1024);
        Future<Integer> operation = channel.read(buffer, 0);
        var numReads = operation.get();
        System.out.println(numReads);
        buffer.flip();
        var chars = new String(buffer.slice().array());
        System.out.println(chars);
        channel.close();

    }

    @Test
    public void handleMessyCode() {
        var raw = "人生如梦, 一樽还酹江月";
        var characterSet = StandardCharsets.UTF_8;
        var bytes = characterSet.encode(raw).array();
        var target = Arrays.copyOfRange(bytes, 0, 11);
        var byteBuf = ByteBuffer.allocate(12);
        var charBuf = CharBuffer.allocate(12);

        byteBuf.put(target);
        byteBuf.flip();

        characterSet.newDecoder().decode(byteBuf, charBuf, true);
        charBuf.flip();

        var tmp = new char[charBuf.length()];
        while (charBuf.hasRemaining()) {
            charBuf.get(tmp);
            System.out.println(new String(tmp));
        }
        System.out.format("limit-position = %d\n", byteBuf.limit() - byteBuf.position());
        System.out.println(Arrays.toString(Arrays.copyOfRange(byteBuf.array(), byteBuf.position(), byteBuf.limit())));
    }

    @Test
    public void intBuffer() {
        IntBuffer intBuffer = IntBuffer.allocate(10);
        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i);
        }
        intBuffer.flip();
        while (intBuffer.hasRemaining()) {
            System.out.print(intBuffer.get() + " ");
        }
        System.out.println();
        intBuffer.flip();
        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i + 1);
        }
        intBuffer.flip();
        intBuffer.limit(5);
        // position < limit
        while (intBuffer.hasRemaining()) {
            System.out.print(intBuffer.get() + " ");
        }
    }

    @Test
    public void readOnlyBuffer() {
        ByteBuffer buffer = ByteBuffer.wrap("readOnly".getBytes());
        while (buffer.hasRemaining()) {
            System.out.print((char) buffer.get());
        }
        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();
        // ReadOnlyBufferException
        readOnlyBuffer.put((byte) 23);
    }

    @Test
    public void testWrap() {
        byte[] bytes = new byte[]{0, 0, 0, 0, 0, 0, 0, 4};
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        while (buffer.hasRemaining()) {
            System.out.print(buffer.get());
        }
        System.out.println();

        bytes[0] = 8;
        buffer.flip();
        while (buffer.hasRemaining()) {
            System.out.print(buffer.get());
        }
        System.out.println();

        buffer.array()[1] = 4;
        for (byte b : bytes) {
            System.out.print(b);
        }
        System.out.println();

        buffer.flip();
        // 84 >> 0000 1000 0000 0100 >> 2048 + 4 (Big Endian)
        long l = buffer.getChar();
        // 2052
        System.out.println(l);
    }

    @Test
    public void mappedByteBuffer() throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(fileName, "rw");
        var fileChannel = randomAccessFile.getChannel();
        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 2);
        mappedByteBuffer.put(0, (byte) 97);
        randomAccessFile.close();
    }

    @Test
    public void fixed() {
        // heap buffers
        System.out.println(new String(Charset.defaultCharset().encode("Hello World").array()));
    }

    @Test
    public void oom() {
        // java.nio.Bits#reserverMemory 中 System.gc() 被显示地调用
        // VM: -XX:+DisableExplicitGC 禁用显示 GC 比如 System.gc()
    }
}

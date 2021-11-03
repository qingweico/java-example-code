package io.buffer;

import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author:qiming
 * @date: 2021/9/27
 */
public class BufferExample {

    final String fileName = "word";

    @Test
    public void gen() throws IOException {
        Random r = new Random();

        var os = new BufferedOutputStream(new FileOutputStream(fileName));

        var t = System.currentTimeMillis();
        for (int i = 0; i < 1_000_0000; i++) {
            for (int j = 0; j < 5; j++) {
                os.write(97 + r.nextInt(5));
            }
            os.write(' ');
        }
        System.out.format("%dms", (System.currentTimeMillis() - t));
        os.close();
    }

    @Test
    public void read() throws IOException {

        var is = new BufferedInputStream(new FileInputStream(fileName));

        var start = System.currentTimeMillis();
        byte[] bytes = new byte[1024 * 4];
        while ((is.read(bytes)) != -1) { }
        System.out.format("%dms", (System.currentTimeMillis() - start));
        is.close();
    }

    @Test
    public void readNio() throws IOException {
        var fileChannel = new FileInputStream(fileName).getChannel();
        var buffer = ByteBuffer.allocate(1024 * 8);
        var start = System.currentTimeMillis();
        while ((fileChannel.read(buffer) != -1)) {
            buffer.flip();
            buffer.clear();
        }
        System.out.format("%dms", (System.currentTimeMillis() - start));
    }

    @Test
    public void readAsync() throws IOException, ExecutionException, InterruptedException {
        var channel = AsynchronousFileChannel.open(Path.of(fileName),
                StandardOpenOption.READ);

        var buffer = ByteBuffer.allocate(1024);
        Future<Integer> operation = channel.read(buffer, 0);
        var numReads = operation.get();
        System.out.println(numReads);
        buffer.flip();
        var chars = new String(buffer.slice().array());
        System.out.println(chars);

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
}

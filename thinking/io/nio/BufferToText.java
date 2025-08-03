package thinking.io.nio;


import cn.qingweico.constants.Constants;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Converting text to and from ByteBuffers
 *
 * @author zqw
 * @date 2021/2/5
 */
class BufferToText {
    private final static int B_SIZE = Constants.KB;

    public static void main(String[] args) throws IOException {
        final String path = "thinking/io/nio/";
        final String fileName = Constants.DEFAULT_FILE_PATH_MAME;
        FileChannel fc = new FileOutputStream(path + fileName).getChannel();
        fc.write(ByteBuffer.wrap("some text".getBytes()));
        fc.close();
        fc = new FileInputStream(path + fileName).getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(B_SIZE);
        fc.read(buffer);
        buffer.flip();

        // Doesn't work
        System.out.println(buffer.asCharBuffer());

        // Decode using this system's default Charset
        buffer.rewind();
        String encoding = System.getProperty("file.encoding");
        System.out.println("Decoded using " + encoding +
                ": " + Charset.forName(encoding).decode(buffer));

        // Or, we could encode with something that will print:
        fc = new FileOutputStream(path + fileName).getChannel();
        fc.write(ByteBuffer.wrap("some text".getBytes(StandardCharsets.UTF_16BE)));
        fc.close();

        // Now try reading again:
        fc = new FileInputStream(path + fileName).getChannel();
        buffer.clear();
        fc.read(buffer);
        buffer.flip();
        System.out.println(buffer.asCharBuffer());

        // Use a CharBuffer to write through:
        fc = new FileOutputStream(path + fileName).getChannel();
        buffer = ByteBuffer.allocate(24);
        buffer.asCharBuffer().put("some text");
        fc.write(buffer);
        fc.close();


        // Read and Display
        fc = new FileInputStream(path + fileName).getChannel();
        buffer.clear();
        fc.read(buffer);
        buffer.flip();
        System.out.println(buffer.asCharBuffer());
    }
}

package thinking.io.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Getting channel from streams
 * <p>
 * FileOutputStream, FileInputStream, RandomAccessFile
 * can be used to generate a FileChannel.
 * </p>
 *
 * @author zqw
 * @date 2021/1/7
 */
public class GetChannel {
    private static final int BSIZE = 1024;

    public static void main(String[] args) throws IOException {
        String path = "thinking/io/nio/";
        String fileName = "data.txt";
        // Write a file
        // Writable
        FileChannel fc = new FileOutputStream(path + fileName).getChannel();
        fc.write(ByteBuffer.wrap("some text ".getBytes()));
        fc.close();

        // Add to the end of the file
        // Can write can read
        fc = new RandomAccessFile(path + fileName, "rw").getChannel();
        // Move to the end
        fc.position(fc.size());
        fc.write(ByteBuffer.wrap("some more".getBytes()));
        fc.close();

        // Read the file
        // Readable
        fc = new FileInputStream(path + fileName).getChannel();
        // allocateDirect(BSIZE)
        ByteBuffer buffer = ByteBuffer.allocate(BSIZE);
        fc.read(buffer);
        buffer.flip();
        while (buffer.hasRemaining()) {
            System.out.print((char) buffer.get());
        }
        fc.close();
    }
}

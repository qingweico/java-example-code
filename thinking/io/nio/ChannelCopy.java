package thinking.io.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Copying the file using channels and buffers
 *
 * @author zqw
 * @date 2021/1/8
 */
class ChannelCopy {
    private static final int BSIZE = 1024;
    static String path = "thinking/io/nio/";

    public static void copyFile(String[] args) throws IOException {
        FileChannel in = new FileInputStream(path + args[0]).getChannel();
        FileChannel out = new FileOutputStream(path + args[1]).getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(BSIZE);
        while (in.read(buffer) != -1) {
            // Prepare for writing
            buffer.flip();
            out.write(buffer);
            // Prepare for reading
            buffer.clear();
        }
    }

    public static void usingTransferCopyFile(String[] args) throws IOException {
        FileChannel in = new FileInputStream(path + args[0]).getChannel();
        FileChannel out = new FileOutputStream(path + args[1]).getChannel();
        in.transferTo(0, in.size(), out);
        // Or
        // out.transferFrom(in, 0, in.size());

    }

    public static void main(String[] args) throws IOException {
        String[] fileNames = {"ChannelCopy.java", "data.txt"};
        copyFile(fileNames);
        usingTransferCopyFile(fileNames);
    }
}

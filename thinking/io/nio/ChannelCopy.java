package thinking.io.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Copying the file using channels and buffers
 *
 * @author:qiming
 * @date: 2021/1/8
 */
public class ChannelCopy {
    private static final int BSIZE = 1024;
    private static final int COUNT = 2;
    public static void copyFile(String[] args) throws IOException {
        FileChannel in = new FileInputStream(args[0]).getChannel();
        FileChannel out = new FileOutputStream(args[1]).getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(BSIZE);
        while(in.read(buffer) != -1){
            // Prepare for writing
            buffer.flip();
            out.write(buffer);
            // Prepare for reading
            buffer.clear();
        }
    }
    public static void usingTransferCopyFile(String[] args) throws IOException {
        FileChannel in = new FileInputStream(args[0]).getChannel();
        FileChannel out = new FileOutputStream(args[1]).getChannel();
        in.transferTo(0, in.size(), out);
        // Or
        // out.transferFrom(in, 0, in.size());

    }
    public static void main(String[] args) throws IOException {
        if(args.length != COUNT){
            System.out.println("argument: sourceFile destFile");
            System.exit(1);
        }
        copyFile(args);

        // usage: java ChannelCopy.java ChannelCopy.java data.txt

    }
}

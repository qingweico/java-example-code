package io.nio;

import cn.qingweico.io.Print;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * The following shows how to quickly copy files using NIO.
 *
 * @author zqw
 * @date 2020/03/23
 */
class NioChannel {
    public static void fastCopy(String src, String dist) throws IOException {
        // Get the input section stream of the source file
        FileInputStream fin = new FileInputStream(src);
        // Get the input section stream of the source file
        FileChannel fileInChannel = fin.getChannel();
        // Get the output section stream of the file
        FileOutputStream fo = new FileOutputStream(dist);
        // Obtain the file channel of the output byte stream
        FileChannel fileOutChannel = fo.getChannel();
        // Allocate 1024 bytes for the buffer
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
        while (true) {
            // Read data from the input channel to the buffer
            int r = fileInChannel.read(buffer);
            // If read returns -1, it means EOF
            if (r == -1) {
                break;
            }
            // Switch read and write
            buffer.flip();
            // Write the buffered file to the output file
            fileOutChannel.write(buffer);
            // Empty the buffer, else position == limit, then r == 0
            buffer.clear();
        }
        fo.close();
        fin.close();
    }

    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
        fastCopy(args[0], args[1]);
        long cost = System.currentTimeMillis() - startTime;
        Print.time("nio", cost);
    }
}

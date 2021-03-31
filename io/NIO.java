package io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * The following shows how to quickly copy files using NIO
 *
 * @author:qiming
 */
public class NIO {
    public static void fastCopy(String src, String dist) throws IOException {
        //获得源文件的输入节字流
        FileInputStream fin = new FileInputStream(src);
        //获取输入字节流的文件通道
        FileChannel fileInChannel = fin.getChannel();
        //获取文件的输出节字流
        FileOutputStream fo = new FileOutputStream(dist);
        //获取输出字节流的文件通道
        FileChannel fileOutChannel = fo.getChannel();
        //为缓冲区分配1024个字节
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
        while (true) {
            //从输入通道中读取数据到缓冲区中 //
            int r = fileInChannel.read(buffer);
            //read返回-1则表示EOF
            if (r == -1) {
                break;
            }
            //切换读写
            buffer.flip();
            //把缓冲的文件写入输出文件中
            fileOutChannel.write(buffer);
            //清空缓冲区
            buffer.clear();
        }
    }

    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
        fastCopy("E:\\ppt\\1.mp4", "X:\\2.mp4");
        System.out.println("本次读写花费的时间为" + (System.currentTimeMillis() - startTime) + "ms");
    }
}

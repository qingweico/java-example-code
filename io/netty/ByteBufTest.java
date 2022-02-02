package io.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.Test;

import static util.Print.print;
import static util.Print.prints;

/**
 * @author zqw
 * @date 2022/1/29
 */
public class ByteBufTest {
    @Test
    public void byteBuf() {
        ByteBuf byteBuf = Unpooled.buffer(10);
        // readerIndex writerIndex
        for(int i = 0;i < byteBuf.capacity();i++) {
            byteBuf.writeByte(i);
        }

        for(int i = 0;i < byteBuf.capacity();i++) {
            prints(byteBuf.getByte(i));
        }
        print();
        for(int i = 0;i < byteBuf.capacity();i++) {
            // readerIndex++
           prints(byteBuf.readByte());
        }
    }
}

package thinking.io;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;

import static cn.qingweico.io.Print.*;


/**
 * @author zqw
 * @date 2021/2/2
 */
class FormattedMemoryInput {
    public static void main(String[] args) throws IOException {
        try {
            DataInputStream in = new DataInputStream(
                    new ByteArrayInputStream(
                            BufferedInputFile
                                    .read("thinking/io/FormattedMemoryInput.java")
                                    .getBytes()
                    ));

            while (true) {
                print((char) in.readByte());
            }
        } catch (EOFException e) {
            println("End of stream");
        }
    }
}

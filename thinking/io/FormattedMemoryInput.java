package thinking.io;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;

import static util.Print.*;


/**
 * @author:周庆伟
 * @date: 2021/2/2
 */
public class FormattedMemoryInput {
    public static void main(String[] args) throws IOException {
        try {
            DataInputStream in = new DataInputStream(
                    new ByteArrayInputStream(
                            BufferedInputFile
                                    .read("src/thinking/io/FormattedMemoryInput.java")
                                    .getBytes()
                    ));

            while (true) {
                printnb((char) in.readByte());
            }
        } catch (EOFException e) {
            print("End of stream");
        }
    }
}

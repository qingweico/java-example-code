package thinking.io;

import java.io.*;

/**
 * @author:qiming
 * @date: 2021/2/3
 */
public class TestEOF {
    public static void main(String[] args) throws IOException {
        DataInputStream in = new DataInputStream(
                             new BufferedInputStream(
                             new FileInputStream("src/thinking/io/TestEOF.java")
                ));


        // If we read characters from the DataInputStream one byte at a time with
        // readByte(), then any byte value is a valid result, so the return value
        // cannot be used to check whether the input is finished. Instead, we can
        // use the available() method to see how many characters are left to read.
        while ((in.available()) != 0) {
            System.out.print((char) in.readByte());
        }
        in.close();
    }
}

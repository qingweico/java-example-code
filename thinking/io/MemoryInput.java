package thinking.io;

import java.io.IOException;
import java.io.StringReader;

/**
 * @author zqw
 * @date 2021/2/2
 */
class MemoryInput {
    public static void main(String[] args) throws IOException {
        StringReader in = new StringReader(
                BufferedInputFile.read("thinking/io/MemoryInput.java")
        );
        int c;
        // read() method does not discard newline characters
        while ((c = in.read()) != -1) {
            System.out.print((char) c);
        }

    }
}

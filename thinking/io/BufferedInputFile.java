package thinking.io;

import java.io.*;

/**
 * @author:qiming
 * @date: 2021/2/2
 */
public class BufferedInputFile {
    // Throw exceptions to console:
    public static String read(String fileName) throws IOException {
        // Reading input by lines:
        BufferedReader in = new BufferedReader(new FileReader(fileName));
        String s;
        StringBuilder sb = new StringBuilder();
        while ((s = in.readLine()) != null) {

            // You have to add newline characters because readLine() deleted them.
            sb.append(s).append("\n");
        }
        in.close();
        return sb.toString();
    }

    public static void main(String[] args) throws IOException {
        System.out.println(read("src/thinking/io/BufferedInputFile.java"));
    }
}

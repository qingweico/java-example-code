package thinking.io;

import java.io.*;

/**
 * @author zqw
 * @date 2021/2/2
 */
public class BufferedInputFile {
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
}

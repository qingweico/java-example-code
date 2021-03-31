package thinking.io;

import java.io.*;

/**
 * @author:周庆伟
 * @date: 2021/2/3
 */
public class BasicFileOutput {
    static String file = "src/thinking/io/out";

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(
                            new StringReader(
                            BufferedInputFile
                                    .read("src/thinking/io/BasicFileOutput.java")
                            ));

        // PrintWriter with a formatting mechanism
        PrintWriter out = new PrintWriter(
                          new BufferedWriter(
                          new FileWriter(file)));

        int lineCount = 1;
        String s;
        while ((s = in.readLine()) != null) {
            out.println(lineCount++ + ": " + s);

        }
        // Call close() for the out display, otherwise you will find that the contents
        // of the buffer will not be flushed and cleared.
        out.close();
        // Show the stored file:
        System.out.println(BufferedInputFile.read(file));

    }
}

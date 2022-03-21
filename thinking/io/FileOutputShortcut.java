package thinking.io;

import java.io.*;

/**
 * @author zqw
 * @date 2021/2/3
 */
class FileOutputShortcut {
    static String file = "thinking/io/FileOutputShortcut.out";

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(
                            new StringReader(
                            BufferedInputFile
                                    .read("thinking/io/FileOutputShortcut.java")
                )
        );
        // JavaSE5 adds a helper constructor in the PrintWriter
        // Here's the shortcut
        PrintWriter out = new PrintWriter(file);
        int lineCount = 1;
        String s;
        while ((s = in.readLine()) != null) {
            out.println(lineCount++ + ": " + s);

        }
        out.close();
        System.out.println(BufferedInputFile.read(file));
    }
}

package thinking.io;

import java.io.*;

/**
 * @author:qiming
 * @date: 2021/1/7
 */
// System.out and System.err are all wrapped up as a PrintStream object,so it
// can be used directly, But it must be wrapped before System.in can be read.

public class StandardIo {
    /**
     * Echoes each line you type directly
     *
     * @throws IOException IOException
     */
    public static void echo() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String s;
        while ((s = bufferedReader.readLine()) != null && s.length() != 0) {
            System.out.println(s);
        }
        // usage: An empty line or Ctrl-z terminal the program
    }

    /**
     * Convert System.out(PrintStream) to PrintWriter
     */
    public static void transform() {
        // Set the second parameter to true to enable auto-clear,
        // otherwise you may not see the output.
        PrintWriter out = new PrintWriter(System.out, true);
        out.println("Hello World");
    }

    public static void redirectInput() throws IOException {
        PrintStream console = System.out;

        BufferedInputStream in = new BufferedInputStream(new FileInputStream("src/thinking/io/StandardIo.java"));
        PrintStream out = new PrintStream(new BufferedOutputStream(new FileOutputStream("src/thinking/io/out")));

        System.setIn(in);
        System.setOut(out);
        System.setErr(out);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s;
        while ((s = br.readLine()) != null) {
            System.out.println(s);
        }
        out.close();
        System.setOut(console);
    }

    public static void main(String[] args) throws IOException {
        redirectInput();
    }
}

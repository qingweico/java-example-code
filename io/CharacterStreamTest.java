package io;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import util.Print;
import util.constants.Constants;

import java.io.*;

/**
 * Read and write files with character streams.
 *
 * @author zqw
 * @date 2020/03/27
 */
public class CharacterStreamTest {
    private final String src = "data";
    private final String target = "destination";
    private long startTime = 0L;

    @BeforeMethod
    public void start() {
        startTime = System.currentTimeMillis();
    }

    @AfterMethod
    public void end() {
        long endTime = System.currentTimeMillis();
        long cost = endTime - startTime;
        Print.time(cost);
    }

    @Test
    public void readChar() throws IOException {
        readChar(src, target);
    }

    @Test
    public void readCharArray() throws IOException {
        readCharArray(src, target);
    }

    @Test
    public void readCharByBuffer() throws IOException {
        readCharByBuffer(src, target);
    }

    @Test
    public void readCharArrayByBuffer() throws IOException {
        readCharArrayByBuffer(src, target);
    }

    @Test
    public void readCharByFileReader() throws IOException {
        readCharByFileReader(src, target);
    }

    @Test
    public void readCharArrayByFileReader() throws IOException {
        readCharArrayByFileReader(src, target);
    }

    @Test
    public void readStringByBuffer() throws IOException {
        readStringByBuffer(src, target);
    }

    @Test
    public void byteStreamToCharacterStream() throws IOException {
        byteStreamToCharacterStream(src, target);
    }

    @Test
    public void printStreamToConsole() throws IOException {
        printStreamToConsole(src);
    }

    @Test
    public void printStreamToFile() throws IOException {
        printStreamToFile(src, target);
    }

    @Test
    public void printWriterToConsole() throws IOException {
        printWriterToConsole(src);
    }

    @Test
    public void printWriterToFile() throws IOException {
        printWriterToFile(src, target);
    }


    /**
     * 基本字符流读取一个字节
     *
     * @throws IOException An {@code IOException} will be thrown If an I/O error occurs
     */
    public static void readChar(String src, String target) throws IOException {
        InputStreamReader fo = new InputStreamReader(new FileInputStream(src));
        OutputStreamWriter fs = new OutputStreamWriter(new FileOutputStream(target));
        int len;
        while ((len = fo.read()) != -1) {
            fs.write(len);
        }
        fs.close();
        fo.close();
    }

    /**
     * 基本字符流读取一个字符数组
     *
     * @throws IOException An {@code IOException} will be thrown If an I/O error occurs
     */
    public static void readCharArray(String src, String target) throws IOException {
        InputStreamReader fo = new InputStreamReader(new FileInputStream(src));
        OutputStreamWriter fs = new OutputStreamWriter(new FileOutputStream(target));
        char[] ch = new char[Constants.KB];
        int len;
        while ((len = fo.read(ch)) != -1) {
            fs.write(ch, 0, len);
        }
        fs.close();
        fo.close();
    }

    /**
     * 高效字符流读取一个字符
     *
     * @throws IOException An {@code IOException} will be thrown If an I/O error occurs
     */
    public static void readCharByBuffer(String src, String target) throws IOException {
        BufferedReader fo = new BufferedReader(new FileReader(src));
        BufferedWriter fs = new BufferedWriter(new FileWriter(target));
        int len;
        while ((len = fo.read()) != -1) {
            fs.write(len);
        }
        fs.close();
        fo.close();
    }

    /**
     * 高效字符流读取一个字符数组
     *
     * @throws IOException An {@code IOException} will be thrown If an I/O error occurs
     */
    public static void readCharArrayByBuffer(String src, String target) throws IOException {
        BufferedReader fo = new BufferedReader(new FileReader(src));
        BufferedWriter fs = new BufferedWriter(new FileWriter(target));
        char[] ch = new char[Constants.KB];
        int len;
        while ((len = fo.read(ch)) != -1) {
            fs.write(ch, 0, len);
        }
        fs.close();
        fo.close();
    }

    /**
     * 基本字符流(FileReader)读写一个字符
     *
     * @throws IOException An {@code IOException} will be thrown If an I/O error occurs
     */
    public static void readCharByFileReader(String src, String target) throws IOException {
        FileReader fo = new FileReader(src);
        FileWriter fs = new FileWriter(target);
        int len;
        while ((len = fo.read()) != -1) {
            fs.write(len);
        }
        fs.close();
        fo.close();
    }

    /**
     * 基本字符流(FileReader)读写一个字符数组
     *
     * @throws IOException An {@code IOException} will be thrown If an I/O error occurs
     */
    public static void readCharArrayByFileReader(String src, String target) throws IOException {
        FileReader fo = new FileReader(src);
        FileWriter fs = new FileWriter(target);
        char[] ch = new char[Constants.KB];
        int len;
        while ((len = fo.read(ch)) != -1) {
            fs.write(ch, 0, len);
        }
        fs.close();
        fo.close();
    }

    /**
     * 高效字符流读写一个字符串
     *
     * @throws IOException An {@code IOException} will be thrown If an I/O error occurs
     */
    public static void readStringByBuffer(String src, String target) throws IOException {
        BufferedReader fo = new BufferedReader(new FileReader(src));
        BufferedWriter fs = new BufferedWriter(new FileWriter(target));
        String s;
        while ((s = fo.readLine()) != null) {
            fs.write(s);
            fs.flush();
        }
        fs.close();
        fo.close();
    }

    public static void byteStreamToCharacterStream(String src, String target) throws IOException {
        InputStreamReader isr = new InputStreamReader(new FileInputStream(src));
        BufferedReader br = new BufferedReader(isr);

        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(target));
        BufferedWriter bw = new BufferedWriter(osw);
        String read;
        while ((read = br.readLine()) != null) {
            bw.write(read);
            bw.newLine();
            bw.flush();
        }
        bw.close();
        br.close();
    }

    public static void printStreamToConsole(String src) throws IOException {
        InputStreamReader isr = new InputStreamReader(new FileInputStream(src));
        BufferedReader br = new BufferedReader(isr);

        PrintStream ps = new PrintStream(System.out);
        String read;
        while ((read = br.readLine()) != null) {
            ps.println(read);
        }
        ps.close();
        br.close();
    }

    public static void printStreamToFile(String src, String target) throws IOException {
        InputStreamReader isr = new InputStreamReader(new FileInputStream(src));
        BufferedReader br = new BufferedReader(isr);

        FileOutputStream fos = new FileOutputStream(target);
        PrintStream ps = new PrintStream(fos);
        String read;
        while ((read = br.readLine()) != null) {
            ps.println(read);
        }
        ps.close();
        br.close();
    }

    public static void printWriterToConsole(String src) throws IOException {
        InputStreamReader isr = new InputStreamReader(new FileInputStream(src));
        BufferedReader br = new BufferedReader(isr);

        PrintWriter pw = new PrintWriter(System.out);
        String read;
        while ((read = br.readLine()) != null) {
            pw.println(read);
        }
        pw.close();
        br.close();
    }

    public static void printWriterToFile(String src, String target) throws IOException {
        InputStreamReader isr = new InputStreamReader(new FileInputStream(src));
        BufferedReader br = new BufferedReader(isr);

        PrintWriter pw = new PrintWriter(target);
        String read;
        while ((read = br.readLine()) != null) {
            pw.println(read);
        }
        pw.close();
        br.close();
    }
}

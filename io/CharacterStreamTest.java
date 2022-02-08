package io;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import util.Constants;

import java.io.*;

/**
 * Read and write files with character streams.
 *
 * @author zqw
 * @date 2020/03/27
 */
public class CharacterStreamTest {
    private final String src = "io/data.txt";
    private final String dest = "io/dest.txt";
    private long startTime = 0L;

    @BeforeMethod
    public void start() {
        startTime = System.currentTimeMillis();
    }

    @AfterMethod
    public void end() {
        long endTime = System.currentTimeMillis();
        System.out.println("time: " + (endTime - startTime) + "ms");
    }

    @Test
    public void readChar() throws IOException {
        readChar(src, dest);
    }

    @Test
    public void readCharArray() throws IOException {
        readCharArray(src, dest);
    }

    @Test
    public void readCharByBuffer() throws IOException {
        readCharByBuffer(src, dest);
    }

    @Test
    public void readCharArrayByBuffer() throws IOException {
        readCharArrayByBuffer(src, dest);
    }

    @Test
    public void readCharByFileReader() throws IOException {
        readCharByFileReader(src, dest);
    }

    @Test
    public void readCharArrayByFileReader() throws IOException {
        readCharArrayByFileReader(src, dest);
    }

    @Test
    public void readStringByBuffer() throws IOException {
        readStringByBuffer(src, dest);
    }

    @Test
    public void byteStreamToCharacterStream() throws IOException {
        byteStreamToCharacterStream(src, dest);
    }

    @Test
    public void printStream() throws IOException {
        printStream(src);
    }

    @Test
    public void printStreamToFile() throws IOException {
        printStreamToFile(src, dest);
    }

    @Test
    public void printWriter() throws IOException {
        printWriter(src);
    }


    /**
     * 基本字符流读取一个字节
     *
     * @throws IOException An {@code IOException} will be thrown If an I/O error occurs
     */
    public static void readChar(String src, String dest) throws IOException {
        InputStreamReader fo = new InputStreamReader(new FileInputStream(src));
        OutputStreamWriter fs = new OutputStreamWriter(new FileOutputStream(dest));
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
    public static void readCharArray(String src, String dest) throws IOException {
        InputStreamReader fo = new InputStreamReader(new FileInputStream(src));
        OutputStreamWriter fs = new OutputStreamWriter(new FileOutputStream(dest));
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
    public static void readCharByBuffer(String src, String dest) throws IOException {
        BufferedReader fo = new BufferedReader(new FileReader(src));
        BufferedWriter fs = new BufferedWriter(new FileWriter(dest));
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
    public static void readCharArrayByBuffer(String src, String dest) throws IOException {
        BufferedReader fo = new BufferedReader(new FileReader(src));
        BufferedWriter fs = new BufferedWriter(new FileWriter(dest));
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
    public static void readCharByFileReader(String src, String dest) throws IOException {
        FileReader fo = new FileReader(src);
        FileWriter fs = new FileWriter(dest);
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
    public static void readCharArrayByFileReader(String src, String dest) throws IOException {
        FileReader fo = new FileReader(src);
        FileWriter fs = new FileWriter(dest);
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
    public static void readStringByBuffer(String src, String dest) throws IOException {
        BufferedReader fo = new BufferedReader(new FileReader(src));
        BufferedWriter fs = new BufferedWriter(new FileWriter(dest));
        String s;
        while ((s = fo.readLine()) != null) {
            fs.write(s);
            fs.flush();
        }
        fs.close();
        fo.close();
    }

    public static void byteStreamToCharacterStream(String src, String dest) throws IOException {
        InputStreamReader isr = new InputStreamReader(new FileInputStream(src));
        BufferedReader br = new BufferedReader(isr);

        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(dest));
        BufferedWriter bw = new BufferedWriter(osw);
        String read;
        while ((read = br.readLine()) != null) {
            bw.write(read);
            bw.flush();
        }
        bw.close();
        br.close();
    }

    public static void printStream(String src) throws IOException {
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

    public static void printStreamToFile(String src, String dest) throws IOException {
        InputStreamReader isr = new InputStreamReader(new FileInputStream(src));
        BufferedReader br = new BufferedReader(isr);
        FileOutputStream fos = new FileOutputStream(dest);
        PrintStream ps = new PrintStream(fos);
        String read;
        while ((read = br.readLine()) != null) {
            ps.println(read);
        }
        ps.close();
        br.close();
    }

    public static void printWriter(String src) throws IOException {
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
}

package thinking.io;

import util.Print;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author zqw
 * @date 2021/2/4
 */
public class UsingRandomAccessFile {
    static String file = "thinking/io/ra.txt";
    static int COUNT = 7;

    static void disPlay() throws IOException {
        RandomAccessFile rf = new RandomAccessFile(file, "r");
        for (int i = 0; i < COUNT; i++) {
            Print.println("Value " + i + ": " + rf.readDouble());
        }
        Print.println(rf.readUTF());
        rf.close();
    }

    public static void main(String[] args) throws IOException {
        RandomAccessFile rf = new RandomAccessFile(file, "rw");
        for (int i = 0; i < COUNT; i++) {
            rf.writeDouble(i * 1.414);
        }
        rf.writeUTF("The end of the file");
        rf.close();
        disPlay();
        rf = new RandomAccessFile(file, "rw");
        // Since double is always 8 bytes long, we need to seek() to find the
        // fifth cool precision value.
        rf.seek(5 * 8);
        rf.writeDouble(47.0001);
        rf.close();
        disPlay();
    }
}

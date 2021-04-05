package thinking.io;

import java.io.IOException;
import java.io.RandomAccessFile;

import static util.Print.print;

/**
 * @author:qiming
 * @date: 2021/2/4
 */
public class UsingRandomAccessFile {
    static String file = "src/thinking/io/r.dat";

    static void disPlay() throws IOException {
        RandomAccessFile rf = new RandomAccessFile(file, "r");
        for (int i = 0; i < 7; i++) {
            print("Value " + i + ": " + rf.readDouble());
        }
        print(rf.readUTF());
        rf.close();
    }

    public static void main(String[] args) throws IOException {
        RandomAccessFile rf = new RandomAccessFile(file, "rw");
        for (int i = 0; i < 7; i++) {
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

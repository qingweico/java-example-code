package thinking.io;

import util.Print;

import java.io.*;

/**
 * @author zqw
 * @date 2021/2/4
 */
public class StoringAndRecoveringData {
    public static void main(String[] args) throws IOException {
        DataOutputStream out = new DataOutputStream(
                new BufferedOutputStream(
                        new FileOutputStream("thinking/io/Data.txt")
                )
        );
        out.writeDouble(3.14159);
        out.writeUTF("That was pi");
        out.writeDouble(1.14);
        out.writeUTF("Square root of 2");
        out.close();
        DataInputStream in = new DataInputStream(
                new BufferedInputStream(
                        new FileInputStream("thinking/io/Data.txt"))
        );
        Print.println(in.readDouble());
        // Only readUTF() will recover the Java-UTF String properly:
        Print.println(in.readUTF());
        Print.println(in.readDouble());
        Print.println(in.readUTF());
    }
}

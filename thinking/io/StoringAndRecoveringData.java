package thinking.io;

import java.io.*;

import static util.Print.print;

/**
 * @author:周庆伟
 * @date: 2021/2/4
 */
public class StoringAndRecoveringData {
    public static void main(String[] args) throws IOException {
        DataOutputStream out = new DataOutputStream(
                new BufferedOutputStream(
                        new FileOutputStream("src/thinking/io/Data.txt")
                )
        );
        out.writeDouble(3.14159);
        out.writeUTF("That was pi");
        out.writeDouble(1.14);
        out.writeUTF("Square root of 2");
        out.close();
        DataInputStream in = new DataInputStream(
                new BufferedInputStream(
                        new FileInputStream("src/thinking/io/Data.txt"))
        );
        print(in.readDouble());
        // Only readUTF() will recover the Java-UTF String properly:
        print(in.readUTF());
        print(in.readDouble());
        print(in.readUTF());


    }
}

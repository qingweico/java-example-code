package thread;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * Alternate print ABCDEF 123456
 *
 * @author:qiming
 * @date: 2020/10/17
 */
public class PipedStreamUsing {
    public static void main(String[] args) throws IOException {
        char[] numbers = "123456".toCharArray();
        char[] words = "ABCDEF".toCharArray();

        PipedInputStream input1 = new PipedInputStream();
        PipedInputStream input2 = new PipedInputStream();
        PipedOutputStream output1 = new PipedOutputStream();
        PipedOutputStream output2 = new PipedOutputStream();

        input1.connect(output2);
        input2.connect(output1);

        String message = "Your turn";

        new Thread(() -> {
            byte[] buffer = new byte[9];
            try {
                for (char n : numbers) {
                    input1.read(buffer);
                    if (new String(buffer).equals(message)) {
                        System.out.print(n);
                    }
                    output1.write(message.getBytes());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }, "t1").start();

        new Thread(() -> {
            byte[] buffer = new byte[9];
            try {
                for (char c : words) {
                    System.out.print(c);
                    output2.write(message.getBytes());
                    input2.read(buffer);

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }, "t2").start();


    }

}

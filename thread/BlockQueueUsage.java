package thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static util.Print.*;

/**
 * 阻塞队列
 * Alternate print ABCDEF 123456
 *
 * @author:qiming
 * @date: 2020/09/05
 */
public class BlockQueueUsage {
    static BlockingQueue<String> q1 = new ArrayBlockingQueue<>(1);
    static BlockingQueue<String> q2 = new ArrayBlockingQueue<>(1);

    public static void main(String[] args) {
        char[] numbers = "123456".toCharArray();
        char[] words = "ABCDEF".toCharArray();

        new Thread(() -> {
            for (char n : numbers) {
                prints(n);
                try {
                    q1.put("ok");
                    q2.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
        new Thread(() -> {
            for (char c : words) {
                try {
                    q1.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                prints(c);
                try {
                    q2.put("ok");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }).start();
    }

}

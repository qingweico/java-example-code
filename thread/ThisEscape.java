package thread;

import java.io.IOException;

/**
 * Do not open threads in constructors!
 *
 * @author:qiming
 * @date: 2021/1/30
 */
public class ThisEscape {
    private final int num = 1;

    public ThisEscape() {

        // Before the constructor finishes executing, the thread may start, and
        // the half the initialization object will be returned with num of 0.
        new Thread(() -> System.out.println(this.num)).start();
    }

    public static void main(String[] args) throws IOException {
        new ThisEscape();
        int n = System.in.read();
        System.out.println(n);
    }
}

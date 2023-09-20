package thinking.concurrency.interrupted;

import thread.pool.ThreadObjectPool;
import util.Print;

import java.io.InputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Interrupted a blocked task by closing the underlying resource.
 *
 * @author zqw
 * @date 2021/1/31
 */
public class CloseResource {
    public static void main(String[] args) throws Exception {
        ExecutorService pool = ThreadObjectPool.newFixedThreadPool(2);
        try (Socket socket = new Socket("localhost", 8080)) {
            InputStream socketInput = socket.getInputStream();
            pool.execute(new IoBlocked(socketInput));
            pool.execute(new IoBlocked(System.in));
            TimeUnit.MILLISECONDS.sleep(100);
            Print.println("Shutting down all threads");
            pool.shutdownNow();
            TimeUnit.SECONDS.sleep(1);
            Print.println("Closing " + socketInput.getClass().getName());
            // Releases blocked thread
            socketInput.close();
            TimeUnit.SECONDS.sleep(1);
            Print.println("Closing " + System.in.getClass().getName());
            // Releases blocked thread
            System.in.close();
        }
    }
}

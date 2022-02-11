package thinking.concurrency.interrupted;

import thread.pool.CustomThreadPool;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import static util.Print.print;

/**
 * Interrupted a blocked task by closing the underlying resource.
 *
 * @author zqw
 * @date 2021/1/31
 */
public class CloseResource {
    public static void main(String[] args) throws Exception {
        ExecutorService pool = CustomThreadPool.newFixedThreadPool(2, 2, 2);
        ServerSocket server = new ServerSocket(8080);
        InputStream socketInput = new Socket("localhost", 8080).getInputStream();
        pool.execute(new IoBlocked(socketInput));
        pool.execute(new IoBlocked(System.in));
        TimeUnit.MILLISECONDS.sleep(100);
        print("Shutting down all threads");
        pool.shutdownNow();
        TimeUnit.SECONDS.sleep(1);
        print("Closing " + socketInput.getClass().getName());
        // Releases blocked thread
        socketInput.close();
        TimeUnit.SECONDS.sleep(1);
        print("Closing " + System.in.getClass().getName());
        // Releases blocked thread
        System.in.close();

    }
}

package thinking.concurrency.interrupted;

import thread.pool.CustomThreadPool;
import util.Constants;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static util.Print.print;

/**
 * Blocked NIO channels automatically respond to interrupts
 *
 * @author zqw
 * @date 2021/2/7
 */
public class NioInterrupted {
    // Interrupting a blocked NIO channel

    private static final String HOST = Constants.LOOP_BACK;
    private static final Integer PORT = Constants.NUM_8080;
    public static void main(String[] args) throws IOException, InterruptedException {
        ExecutorService pool = CustomThreadPool.newFixedThreadPool(2);
        InetSocketAddress isa = new InetSocketAddress(HOST, PORT);
        SocketChannel sc1 = SocketChannel.open(isa);
        SocketChannel sc2 = SocketChannel.open(isa);
        Future<?> f = pool.submit(new NioBlocked(sc1));
        pool.submit(new NioBlocked(sc2));
        pool.shutdown();
        TimeUnit.SECONDS.sleep(1);
        // Produce an interrupted via cancel:
        f.cancel(true);
        TimeUnit.SECONDS.sleep(1);
        // Release the block by closing the channel:
        sc2.close();


    }

}

class NioBlocked implements Runnable {
    private final SocketChannel sc;

    public NioBlocked(SocketChannel sc) {
        this.sc = sc;
    }

    @Override
    public void run() {
        try {
            print("Waiting for read() in " + this);
            sc.read(ByteBuffer.allocate(1));
        } catch (ClosedByInterruptException e) {
            print("ClosedByInterruptException");
        } catch (AsynchronousCloseException e) {
            print("AsynchronousCloseException");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        print("Exiting NIOBlocked.run() " + this);
    }

}

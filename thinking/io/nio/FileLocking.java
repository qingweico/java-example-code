package thinking.io.nio;

import java.io.FileOutputStream;
import java.nio.channels.FileLock;
import java.util.concurrent.TimeUnit;

import static util.Print.print;

/**
 * A simple column example of file lock-up
 * JDK1.4 introduces file locking, which allows synchronous access to a file that is the
 * most shared resource, file locks are visible to other operating system processes because
 * Java's file locks map directly to the local operating system's locking facility.
 *
 * @author zqw
 * @date 2021/2/4
 */

class FileLocking {
    public static void main(String[] args) throws Exception {
        FileOutputStream fos = new FileOutputStream("file.txt");
        // By calling tryLock () or Lock() on the FileChannel, you can get the FileLock of
        // the entire file, but tryLock() is non-blocking, while Lock() is blocking.
        FileLock fl = fos.getChannel().tryLock();
        if (fl != null) {
            print("Locked File");
            TimeUnit.MILLISECONDS.sleep(100);
            fl.release();
            print("Release Lock");
        }
        fos.close();

        // The type of lock (shared or exclusive) can be queried through isShared().
        // fl.isShared();
    }

    // Locks part of the file
    // tryLock(long position, long size, boolean shared)
    // lock(long position, long size, boolean shared)

    // A lock with a fixed size does not change as the file size changes, locking only a certain
    // area, whereas a lock with no arguments locks the entire file, even as the file grows.
}

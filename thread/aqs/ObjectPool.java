package thread.aqs;

import oak.User;
import util.RandomDataGenerator;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.Semaphore;
import java.util.function.Function;

/**
 * The object pool based on semaphore
 *
 * @author zqw
 * @date 2022/2/4
 */
public class ObjectPool<T, R> {
    final List<T> pool;
    final Semaphore semaphore;

    public ObjectPool(int size, T t) {
        pool = new Vector<>();
        for (int i = 0; i < size; i++) {
            pool.add(t);
        }
        semaphore = new Semaphore(size);
    }

    R exec(Function<T, R> func) throws InterruptedException {
        T t = null;
        try {
            semaphore.acquire();
            t = pool.remove(0);
            return func.apply(t);
        } finally {
            pool.add(t);
            semaphore.release();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        User user = new User();
        user.setUsername(RandomDataGenerator.randomName());
        int size = 10;
        ObjectPool<User, String> objectPool = new ObjectPool<>(size, user);
        for (int i = 0; i < size; i++) {
            // User type -> String
            String userName = objectPool.exec(User::getUsername);
            System.out.println(userName);
        }
    }
}

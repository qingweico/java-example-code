package thread.aqs;

import org.springframework.util.Assert;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.Semaphore;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * The object pool based on semaphore
 * Apache 提供的池化包请参考 Commons Pool2 {@link org.apache.commons.pool2.ObjectPool}
 * Redis 的客户端 jedis 基于 Commons Pool2 实现 {@see redis.clients.jedis.JedisFactory}
 *
 * @author zqw
 * @date 2022/2/4
 */
public class ObjectPool<T, R> {
    final List<T> pool;
    final Semaphore semaphore;


    /**
     * 单个对象 多个引用
     *
     * @param size 对象池的大小
     * @param t    对象池中将持有的实例对象类型
     */
    public ObjectPool(int size, T t) {
        Assert.notNull(t, "t cannot be null");
        /*使用Vector是因为元素的移除操作并不是原子操作,确保在多线程环境中数据安全*/
        pool = new Vector<>();
        for (int i = 0; i < size; i++) {
            pool.add(t);
        }
        // 信号量许可数即是池中对象引用的数量;即控制连接池中的并发数
        semaphore = new Semaphore(size);
    }

    /*Not return value*/

    public void exec(Consumer<T> consumer) throws InterruptedException {
        T t = null;
        try {
            semaphore.acquire();
            t = pool.remove(0);
            consumer.accept(t);
        } finally {
            pool.add(t);
            semaphore.release();
        }
    }

    /*Return value*/

    public R submit(Function<T, R> func) throws InterruptedException {
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
}

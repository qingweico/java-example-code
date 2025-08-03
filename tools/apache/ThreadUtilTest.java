package tools.apache;

import org.apache.commons.lang3.ThreadUtils;
import org.junit.Test;
import cn.qingweico.concurrent.pool.ThreadObjectPool;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author zqw
 * @date 2023/9/19
 */
public class ThreadUtilTest {
    @Test
    public void printAll() {
        System.out.println(ThreadUtils.getAllThreads());
        System.out.println(ThreadUtils.getAllThreadGroups());
        System.out.println(ThreadUtils.getSystemThreadGroup());
    }
    @Test
    public void join() throws InterruptedException {
        ThreadObjectPool threadPool = new ThreadObjectPool(1, 1);
        AtomicReference<Thread> tr = new AtomicReference<>();
        threadPool.execute(() -> {
            try {
                tr.set(Thread.currentThread());
                ThreadUtils.sleep(Duration.ofSeconds(3));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("t thread");
        });
        Thread t;
        for (;;) {
           if( tr.get() != null) {
               break;
           }
        }
        t = tr.get();
        // (join)阻塞主线程2s 无论线程t有没有结束都会继续执行
        ThreadUtils.join(t, Duration.ofSeconds(2));
        System.out.println("2s after print");
    }
}

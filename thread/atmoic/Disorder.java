package thread.atmoic;

import cn.qingweico.concurrent.pool.ThreadObjectPool;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

/**
 * The reason bytecode instructions can be out of order is to improve efficiency.
 * <p>
 * principle: Bytecode instructions can be exchanged on the premise that
 * it does not affect the final consistency of the single thread.
 * class文件中前后的字节码,并不意味着前者一定在后者之前执行;如果后者没有观测前者的运行结果,即后者
 * 没有数据依赖于前者,那么它们可能会被重排序
 * 线程的as-if-serial: 看上去是序列化(顺序)执行的,但是编译器或者CPU都会对其进行乱序执行;
 * 单线程的重排序,必须保证最终一致性
 *
 * @author zqw
 * @date 2021/1/30
 */
class Disorder {
    private static int x = 0, y = 0, a = 0, b = 0;
    static ExecutorService pool = ThreadObjectPool.newFixedThreadPool(2);

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            CountDownLatch latch = new CountDownLatch(2);
            pool.execute(() -> {
                a = 1;
                x = b;
                latch.countDown();
            });

            pool.execute(() -> {
                b = 1;
                y = a;
                latch.countDown();
            });
            latch.await();

            if (x == 0 && y == 0) {
                System.err.println("第[" + i + "]次执行: " + "x = " + x + ", y = " + y);
                break;
            }
        }
        pool.shutdown();
    }
}

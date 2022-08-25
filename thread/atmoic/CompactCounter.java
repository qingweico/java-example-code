package thread.atmoic;

import thread.pool.CustomThreadPool;
import util.Constants;
import util.Print;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;

/**
 * {@link AtomicLongFieldUpdater} 对对象中long字段更新原子操作
 * AtomicLongFieldUpdater 处理 long 类型的字段,
 * AtomicIntegerFieldUpdater 处理 int 类型的字段
 * AtomicReferenceFieldUpdater 处理 Object 类型的字段
 *
 * @author zqw
 * @date 2022/7/3
 */
class CompactCounter {

    /*counter 必须是 volatile 类型的*/

    private volatile long counter;
    private static final AtomicLongFieldUpdater<CompactCounter> UPDATER =
            AtomicLongFieldUpdater.newUpdater(CompactCounter.class, "counter");

    public void increase() {
        long old = UPDATER.get(this);
        long newValue = UPDATER.incrementAndGet(this);
        Print.grace(old, newValue);
    }

    public static void main(String[] args) {
        ExecutorService pool = CustomThreadPool.newFixedThreadPool(20, true);
        CompactCounter cc = new CompactCounter();
        for (int i = 0; i < Constants.TWENTY; i++) {
            pool.execute(cc::increase);
        }
        pool.shutdown();
    }
}

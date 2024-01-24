package thread.aqs;

import thread.pool.ThreadObjectPool;
import util.constants.Constants;
import util.Print;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * The mutex based on AQS
 * 队列同步器: AbstractQueuedSynchronizer 是用来构建锁或者其他同步组件的基础框架
 * 同步器是实现锁的或者任意同步组件的关键
 * 锁的实现中聚合同步器,利用同步器实现锁的语义
 * ################################ 二者的关系 ################################
 * 锁是面向使用者的,它定义了使用者与锁交互的接口,隐藏了实现细节
 * 同步器面向的是锁的实现者,简化了锁的实现方式,屏蔽了同步状态管理,线程的排队,等待与唤醒等底层操作
 * ################################ 模板方法 ################################
 * 同步器的设计是基于模板方法模式的
 * --------- 独占式
 * acquire(int arg): 调用重写的tryAcquire方法
 * acquireInterruptibly(int arg): 当前线程可以被中断
 * tryAcquireNanos(int arg, long nacos) 在 acquireInterruptibly 基础上增加了超时限制
 * release(int arg)
 * --------- 共享式
 * acquireShared(int arg)
 * acquireSharedInterruptibly(int arg)
 * tryAcquireSharedNanos(int arg,long nacos)
 * releaseShared(int arg)
 * ---------
 * getQueuedThreads() 获取等待在同步队列上的线程集合
 * ################################ Node 属性 ################################
 * --------- waitStatus
 * CANCELLED: 1 由于在同步队列中等待的线程等待超时或者被中断,需要从同步队列中取消等待,节点进入该状态将不会改变
 * SIGNAL: -1 后继节点的线程处于等待状态,而当前节点的线程如果释放了同步状态或者被取消,将会通知后继节点
 * 使后继节点的线程得以运行
 * CONDITION: -2 节点在等待队列中,节点线程等待在 Condition 上,当其他线程对 Condition 调用了 signal 方法
 * 后,该节点将会从等待队列中转移到同步队列中 加入到对同步状态的获取中
 * PROPAGATE: -3 表示下一次共享式同步状态获取将会无条件地被传播下去
 * INITIAL: 0 初始状态
 * --------- prev
 * 前驱节点;当节点加入同步队列时被设置(尾部添加)
 * --------- next
 * 后继节点
 * --------- nextWaiter
 * 等待队列中的后继节点;如果当前节点是共享的,那么这个字段将是一个 SHARED 常量,也就是说节点类型(独占和共享)
 * 和等待队列中的后继节点共用同一个字段
 * --------- thread
 * 获取同步状态的线程
 *
 * @author zqw
 * @date 2021/10/17
 * @see Semaphore
 */
public class Mutex extends AbstractQueuedSynchronizer {
    private static int shared = 0;

    @Override
    protected boolean tryAcquire(int arg) {
        return compareAndSetState(0, 1);
    }

    @Override
    protected boolean tryRelease(int arg) {
        setState(0);
        return true;
    }

    @Override
    protected boolean isHeldExclusively() {
        return getState() == 1;
    }

    public static void main(String[] args) {
        Mutex mutex = new Mutex();
        ExecutorService pool = ThreadObjectPool.newFixedThreadPool(10, 10, 1);
        CountDownLatch latch = new CountDownLatch(10);
        for (int i = 0; i < Constants.TEN; i++) {
            pool.execute(() -> {
                mutex.acquire(0);
                for (int j = 0; j < Constants.NUM_1000; j++) {
                    shared++;
                }
                mutex.release(0);
                latch.countDown();
            });
        }
        pool.shutdown();
        try {
            latch.await();
        } catch (InterruptedException e) {
            Print.err(e.getMessage());
        }
        Print.grace("shared", shared);
    }
}

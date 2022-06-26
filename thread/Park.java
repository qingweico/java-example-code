package thread;

import thread.pool.CustomThreadPool;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author zqw
 * @date 2021/9/8
 */
class Park {
   public static void main(String[] args) {

      CountDownLatch latch = new CountDownLatch(1);
      ExecutorService pool = CustomThreadPool.newFixedThreadPool(2, 2, 1);
      final Thread[] t1 = {null};
      pool.execute(() -> {
         // ensure t1 go first!
         System.out.println("park before t1");
         latch.countDown();
         t1[0] = Thread.currentThread();
         LockSupport.park();
         System.out.println("park after t1");
      });

      pool.execute(() -> {
         try {
            latch.await();
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
         System.out.println("un-park before t2");
         try {
            TimeUnit.SECONDS.sleep(1);
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
         LockSupport.unpark(t1[0]);
         System.out.println("un-park after t2");
      });
      pool.shutdown();
      // if there are no using CountDownLatch, may be output
      // un-park before t2
      // park before t1
      // un-park after t2
      // park after t1
   }
}

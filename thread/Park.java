package thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author:qiming
 * @date: 2021/9/8
 */
public class Park {
   public static void main(String[] args) {

      CountDownLatch latch = new CountDownLatch(1);
      Thread t1 = new Thread(() -> {
         // ensure t1 go first!
         System.out.println("park before t1");
         latch.countDown();
         LockSupport.park();
         System.out.println("park after t1");
      });



      Thread t2 = new Thread(() -> {
         try {
            latch.await();
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
         System.out.println("unpark before t2");
         try {
            TimeUnit.SECONDS.sleep(1);
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
         LockSupport.unpark(t1);
         System.out.println("unpark after t2");
      });




      t1.start();
      t2.start();








      // if there no using CountDownLatch, may be output
      // unpark before t2
      // park before t1
      // unpark after t2
      // park after t1
   }
}

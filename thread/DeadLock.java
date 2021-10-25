package thread;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author:qiming
 * @date: 2021/10/17
 */
public class DeadLock {
   static class Friend {
      private final String name;
      private final ReentrantLock lock = new ReentrantLock();

      public Friend(String name) {
         this.name = name;
      }
      public String getName() {
         return name;
      }
      public void bow(Friend bow) {
         lock.lock();
         System.out.format("%s: %s bow to me!\n", this.name, bow.getName());
         // Dead lock! Didn't release the lock, you should firstly
         //  release the lock and then release the resource.
         bow.release(this);
         lock.unlock();
      }
      public void release(Friend bow) {
         lock.lock();
         System.out.format("%s: %s return me!\n", this.name, bow.getName());
         lock.unlock();
      }
   }

   public static void main(String[] args) {
      final Friend hong = new Friend("hong");
      final Friend lan = new Friend("lan");
      new Thread(() -> {
         hong.bow(lan);
      }).start();
      new Thread(() -> {
         lan.bow(hong);
      }).start();
   }
}

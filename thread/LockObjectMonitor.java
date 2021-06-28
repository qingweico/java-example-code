package thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author:qiming
 * @date: 2021/6/27
 */
public class LockObjectMonitor {

   static List<Thread> list = new ArrayList<>();

   static ReentrantLock lock = new ReentrantLock();

   public static void main(String[] args) {
      for(int i = 0; i < 10;i++) {
         Thread thread = new Thread(() -> {
            // Don't get lock and Enter EntryList
            // The EntryList of the ReentrantLock follows the FIFO
            lock.lock();
            System.out.println("\t" + Thread.currentThread().getName() + " -----> thread execute...\t");
            try {
               TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
            lock.unlock();
         },"t" + i);

         list.add(thread);
      }

      lock.lock();
      System.out.println("start sequence:");
      for(Thread thread : list) {
         System.out.println("\t" + thread.getName() + "---->");
         thread.start();

         try {
            TimeUnit.MILLISECONDS.sleep(1);
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
      }
      lock.unlock();
      System.out.println("Awake sequence:");
   }
}

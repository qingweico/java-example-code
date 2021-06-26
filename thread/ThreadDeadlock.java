package thread;

/**
 * @author:qiming
 * @date: 2021/6/17
 */
public class ThreadDeadlock extends Thread{
   private static final Object objA = new Object();
   private static final Object objB = new Object();
   private final boolean flag;
   public ThreadDeadlock(boolean flag){
      this.flag = flag;
   }
   @Override
   public void run() {
      if(flag){
         synchronized (objA){
            System.out.println("objA...");
            synchronized (objB){
               System.out.println("objB...");
            }
         }
      }
      else {
         synchronized (objB){
            System.out.println("objB...");
            synchronized (objA){
               System.out.println("objA...");
            }
         }
      }
   }
   public static void main(String[] args) {
      ThreadDeadlock d1 = new ThreadDeadlock(true);
      ThreadDeadlock d2 = new ThreadDeadlock(false);
      d1.start();
      d2.start();
   }
}

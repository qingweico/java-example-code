package jvm;

/**
 * @author zqw
 * @date 2021/11/10
 */
public class SysGc {
   public static void main(String[] args) throws InterruptedException {
      new SysGc();
      System.gc();
      System.runFinalization();
   }

   @Override
   protected void finalize() throws Throwable {
      super.finalize();
      System.out.println("finalize!");
   }
}

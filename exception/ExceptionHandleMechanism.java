package exception;

/**
 * @author:qiming
 * @date: 2021/10/17
 */
public class ExceptionHandleMechanism {

   private static int doWork() {
      try {
         int i = 10 / 0;
      } catch (ArithmeticException e) {
         System.out.println("ArithmeticException: " + e);
         return 0;
      }catch (Exception e) {
         System.out.println("Exception: " + e);
         return 1;
      }finally {
         System.out.println("Finally");
         return 2;
      }
   }
   public static void main(String[] args) {
      System.out.println(doWork());
   }
}

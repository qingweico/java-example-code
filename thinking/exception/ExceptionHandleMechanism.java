package thinking.exception;

import annotation.Pass;

/**
 * @author zqw
 * @date 2021/10/17
 */
@Pass
@SuppressWarnings("all")
class ExceptionHandleMechanism {

   private static int doWork() {
      try {
         int i = 10 / 0;
         return 3;
      } catch (ArithmeticException e) {
         System.out.println("ArithmeticException: " + e);
         // finally 中的 return 会覆盖掉 try 或者 catch 中的返回值
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
      // 2
      System.out.println(doWork());
   }
}

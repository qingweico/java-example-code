package thinking.exception;

/**
 * --------------- 测试异常模式和正常模式的性能 ---------------
 * @author zqw
 * @date 2021/10/17
 */
public class ExceptionPerformance {
   public static void testException(String[] array) {
      try {
         System.out.println(array[0]);
      }catch (NullPointerException e) {
         System.out.println("array cannot be null");
      }
   }
   public static void testIf(String[] array) {
      if(array != null) {
         System.out.println(array[0]);
      }else {
         System.out.println("array cannot be null");
      }
   }

   public static void main(String[] args) {
      var start = System.nanoTime();
      testException(null);
      System.out.println("testException time: " + (System.nanoTime() - start) + "ms");


      start = System.nanoTime();
      testIf(null);
      System.out.println("testIf time: " + (System.nanoTime() - start) + "ms");
   }
}

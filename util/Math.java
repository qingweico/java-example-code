package util;

/**
 * @author:qiming
 * @date: 2021/7/11
 */
public class Math {
   // Calculate the PI
   public static double calcPi() {
      double re = 0;
      for (int i = 1; i < 10000; i++) {
         re += ((i & 1) == 0 ? -1 : 1) * 1.0 / (2 * i - 1);
      }
      return re * 4;
   }
   public static void forLoop() {
      int i;
      int n = 1;
      for(i = 1;n != 2;i++){
         System.out.println(i);
         ++n;
      }
      //1 2
      System.out.println(i);
   }
}

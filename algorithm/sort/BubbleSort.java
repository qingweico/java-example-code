package algorithm.sort;

/**
 * @author:qiming
 * @date: 2021/10/16
 */
public class BubbleSort implements MutableSorter{
   @Override
   public void sort(int[] A) {
      for(int i = A.length - 1; i >= 0;i--) {
         bubble(A, i + 1);
      }
   }

   private static void bubble(int[] A, int r) {
      for(int k = 0; k < r - 1;k++) {
         if(A[k] > A[k + 1]) {
            Tools.swap(A, k, k + 1);
         }
      }
   }
}

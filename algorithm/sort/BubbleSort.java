package algorithm.sort;

import util.Tools;

/**
 * @author zqw
 * @date 2021/10/16
 */
public class BubbleSort implements MutableSorter{
   @Override
   public void sort(int[] e) {
      for(int i = e.length - 1; i >= 0; i--) {
         bubble(e, i + 1);
      }
   }

   private static void bubble(int[] ai, int r) {
      for(int k = 0; k < r - 1;k++) {
         if(ai[k] > ai[k + 1]) {
            Tools.swap(ai, k, k + 1);
         }
      }
   }
}

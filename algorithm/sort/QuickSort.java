package algorithm.sort;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zqw
 * @date 2021/10/16
 */
public class QuickSort implements IMutableSorter{
   @Override
   public List<Integer> sort(List<Integer> ci) {
     return quickSort(ci);
   }
   private List<Integer> quickSort(List<Integer> ci) {
      if(ci.size() <= 1) {
         return ci;
      }
      var pivot = ci.get(0);
      var left = ci.stream().filter(i -> i < pivot)
              .collect(Collectors.toList());
      var mid = ci.stream().filter(i -> i.equals(pivot))
              .collect(Collectors.toList());
      var right = ci.stream().filter(i -> i > pivot)
              .collect(Collectors.toList());
      left = quickSort(ci);
      right = quickSort(ci);
      left.addAll(mid);
      left.addAll(right);
      return left;
   }
}

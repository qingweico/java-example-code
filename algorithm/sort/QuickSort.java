package algorithm.sort;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author:qiming
 * @date: 2021/10/16
 */
public class QuickSort implements IMutableSorter{
   @Override
   public List<Integer> sort(List<Integer> A) {
     return quickSort(A);
   }
   private List<Integer> quickSort(List<Integer> A) {
      if(A.size() <= 1) {
         return A;
      }
      var pivot = A.get(0);
      var left = A.stream().filter(i -> i < pivot)
              .collect(Collectors.toList());
      var mid = A.stream().filter(i -> i.equals(pivot))
              .collect(Collectors.toList());
      var right = A.stream().filter(i -> i > pivot)
              .collect(Collectors.toList());
      left = quickSort(A);
      right = quickSort(A);
      left.addAll(mid);
      left.addAll(right);
      return left;
   }
}

package thinking.genericity;

import util.Print;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * mutable parameter and generic method
 * <p>
 * mutable generic parameters: T ...args
 * 可变参数与泛型 - 可能出现堆污染(泛型限制类型失效)
 * @author zqw
 * @date 2021/4/9
 */
class GenericVarargs {

   // Arrays.asList();
   // Heap Pollution is possible when a mutable generic
   // parameter points to a non-generic parameter!

   @SafeVarargs
   @SuppressWarnings("varargs")
   public static <T> List<T> markList(T ...args) {
      return new ArrayList<>(Arrays.asList(args));
   }

   public static void main(String[] args) {
      // Bad!
      List<Object> ls = markList("A",2);
      System.out.println(ls);
      List<String> l = markList("A");
      System.out.println(l);
      ls = markList("A", "B", "C");
      System.out.println(ls);
      ls = markList("ABCDEFGHIJKLMNOPQRSTUVWXYZ".split(""));
      System.out.println(ls);
      heapPollution();
   }

   private static void heapPollution() {
      List<String>[] el = alterEl(Arrays.asList("A", "B", "C"), Arrays.asList("1", "2", "3"));
      if(el != null) {
         for(List<String> e : el) {
            for(String str : e) {
               Print.prints(str);
            }
         }
      }
   }

   /*@SafeVarargs @since JDK9 可以加在private方法上*/


   @SafeVarargs
   private static List<String>[] alterEl(List<String>... lists) {
      if(Array.getLength(lists) == 0) {
         return null;
      }
      ((Object[]) lists)[0] = Arrays.asList(1, 2, 3);
      return lists;
   }
}

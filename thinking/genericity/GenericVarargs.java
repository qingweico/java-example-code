package thinking.genericity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * mutable parameter and generic method
 *
 * mutable generic parameters: T ...args
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

   }
}

package thinking.genericity;

/**
 * @author:qiming
 * @date: 2021/4/10
 */
public class CounterObject {
   private static long counter = 0;
   private final long id = counter++;
   public long id() {return id;}
   public String toString() {
      return "CounterObject: " + id;
   }
}

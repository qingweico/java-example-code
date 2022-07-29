package thinking.genericity;

/**
 * @author zqw
 * @date 2021/4/10
 */
class CounterObject {
   private static long counter = 0;
   private final long id = counter++;
   public long id() {return id;}
   @Override
   public String toString() {
      return "CounterObject: " + id;
   }
}

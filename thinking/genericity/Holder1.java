package thinking.genericity;

/**
 * @author zqw
 * @date 2021/4/9
 */
public class Holder1 {
   private Automobile automobile;
   public Holder1(Automobile a) {
      automobile = a;
   }
   Automobile get() {return automobile;}
}
class Automobile {}

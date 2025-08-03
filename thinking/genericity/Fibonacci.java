package thinking.genericity;

import cn.qingweico.supplier.Generator;
import cn.qingweico.io.Print;
import cn.qingweico.constants.Constants;

/**
 * @author zqw
 * @date 2021/4/9
 */

/*Generate a Fibonacci sequence*/

public class Fibonacci implements Generator<Integer> {

   private int count = 0;
   @Override
   public Integer next() {
      return fib(count++);
   }

   private int fib(int n) {
      if(n < Constants.TWO) {
          return 1;
      }
      return fib(n - 2) + fib(n - 1);
   }

   public static void main(String[] args) {
      Fibonacci gen = new Fibonacci();
      int loopCount = 10;
      for(int i = 0;i < loopCount;i++) {
         Print.prints(gen.next());
      }
   }
}

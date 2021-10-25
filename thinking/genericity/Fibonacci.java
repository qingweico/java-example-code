package thinking.genericity;

import util.Generator;

/**
 * @author:qiming
 * @date: 2021/4/9
 */
// Generate a Fibonacci sequence
public class Fibonacci implements Generator<Integer> {

   private int count = 0;
   @Override
   public Integer next() {
      return fib(count++);
   }

   private int fib(int n) {
      if(n < 2) {
          return 1;
      }
      return fib(n - 2) + fib(n - 1);
   }

   public static void main(String[] args) {
      Fibonacci gen = new Fibonacci();
      for(int i = 0;i < 10;i++) {
         System.out.print(gen.next() + " ");
      }
   }
}

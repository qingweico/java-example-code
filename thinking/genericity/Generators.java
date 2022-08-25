package thinking.genericity;

import thinking.genericity.coffee.Coffee;
import thinking.genericity.coffee.CoffeeGenerator;
import util.Generator;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Generic methods for Generator
 *
 * @author zqw
 * @date 2021/4/9
 */
public class Generators {
   public static <T>Collection<T> fill(Collection<T> coll, Generator<T> gen, int n) {
      for(int i = 0;i < n;i++) {
         coll.add(gen.next());
      }
      return coll;
   }

   public static void main(String[] args) {
      Collection<Coffee> coffee = fill(new ArrayList<>(),
              new CoffeeGenerator<>(), 4);
      for(Coffee c : coffee) {
         System.out.println(c);
      }
      Collection<Integer> numbers = fill(new ArrayList<>(), new Fibonacci(), 12);
      for(int i : numbers) {
         System.out.print(i + " ");
      }
   }
}

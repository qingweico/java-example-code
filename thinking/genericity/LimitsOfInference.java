package thinking.genericity;

import thinking.holding.pets.Person;
import thinking.holding.pets.Pet;
import util.New;
import util.Print;

import java.util.List;
import java.util.Map;

/**
 * @author zqw
 * @date 2021/4/9
 */
class LimitsOfInference {
   static void f(Map<Person, List<? extends Pet>> petPerson) {
      Print.toPrint(petPerson);
   }

   public static void main(String[] args) {
      // Does not compile (before JDK8);f(New.<Person, List<Pet>>map());
      f(New.map());
      // TODO
   }
}

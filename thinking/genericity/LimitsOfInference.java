package thinking.genericity;

import thinking.holding.pets.Person;
import thinking.holding.pets.Pet;
import util.New;

import java.util.List;
import java.util.Map;

/**
 * @author:qiming
 * @date: 2021/4/9
 */
public class LimitsOfInference {
   static void f(Map<Person, List<? extends Pet>> petPerson) {
      System.out.println(petPerson);
   }

   public static void main(String[] args) {
      f(New.map()); // Does not compile (before JDK8)
      Map<Object, Object> map = New.map();
   }
}

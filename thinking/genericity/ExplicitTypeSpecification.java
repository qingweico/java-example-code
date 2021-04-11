package thinking.genericity;

import thinking.holding.pets.Person;
import thinking.holding.pets.Pet;
import util.New;

import java.util.List;
import java.util.Map;

/**
 * Display type description but rarely used!
 *
 * @author:qiming
 * @date: 2021/4/9
 */
public class ExplicitTypeSpecification {
   static void f(Map<Person, List<Pet>> petPerson) {}


   // After JDK7, the compiler can infer the display type parameters.
   public static void main(String[] args) {
      f(New.<Person, List<Pet>>map());
   }

   // When map method non-static
   // f(this.<Person, List<Pet>>map());
}

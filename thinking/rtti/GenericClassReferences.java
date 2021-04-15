package thinking.rtti;

/**
 * @author:qiming
 * @date: 2021/4/11
 */
public class GenericClassReferences {
   public static void main(String[] args) {
      Class intClass = int.class;

      // Using generic syntax allows the compiler
      // to enforce additional type checking.
      Class<Integer> genericIntClass = int.class;
      // Same thing
      genericIntClass = Integer.class;
      intClass = double.class;

      // Illegal!
      // genericIntClass = double.class;


      // Doesn't work!
      // An Integer Class object is not a subclass of a Number Class object!
      // Class<Number> genericNumberClass = int.class;

   }
}

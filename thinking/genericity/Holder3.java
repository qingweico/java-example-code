package thinking.genericity;

/**
 * @author:qiming
 * @date: 2021/4/9
 */
public class Holder3<T> {
   private T a;
   public Holder3(T a) {
      this.a = a;
   }
   public void set() {
      this.a = a;
   }
   public T get() {
      return a;
   }

   public static void main(String[] args) {
      Holder3<Automobile> h3 = new Holder3<>(new Automobile());
      // Not cast need
      Automobile a = h3.get();

      // Error
      // h3.set("Not an Automobile");
      // h3.set(1);

   }
}

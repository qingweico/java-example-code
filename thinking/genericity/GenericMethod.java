package thinking.genericity;

/**
 * <p>Generic method</p>
 *
 * @author zqw
 * @date 2021/4/9
 */
// Whether you have a generic method or not is independent of
// whether the class you are in has a generic method.
public class GenericMethod {
   public <T> void f(T x) {
      System.out.println(x.getClass().getName());
   }

   public static void main(String[] args) {
      GenericMethod gm = new GenericMethod();
      gm.f(" "); gm.f(1); gm.f(1.0);
      gm.f(1.0f); gm.f('c'); gm.f(gm);

   }
}

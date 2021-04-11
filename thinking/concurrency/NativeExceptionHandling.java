package thinking.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static util.Print.print;

/**
 * @author:qiming
 * @date: 2021/4/9
 * {ThrowsException}
 */
public class NativeExceptionHandling {

   public static void main(String[] args) {
      try {
         ExecutorService exec = Executors.newCachedThreadPool();
         exec.execute(new ExceptionThread());
      }catch (RuntimeException ue){
         // This statement will NOT execute!
         print("Exception has been handled");
      }
   }
}

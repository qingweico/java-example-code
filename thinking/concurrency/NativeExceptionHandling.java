package thinking.concurrency;

import thread.pool.ThreadPoolBuilder;
import util.Print;

import java.util.concurrent.ExecutorService;

/**
 * @author zqw
 * @date 2021/4/9
 * {ThrowsException}
 */
class NativeExceptionHandling {

   public static void main(String[] args) {
      try {
         ExecutorService pool = ThreadPoolBuilder.builder().build();
         pool.execute(new ExceptionThread());
      }catch (RuntimeException ue){
         // This statement will NOT execute!
         Print.println("Exception has been handled");
      }
   }
}

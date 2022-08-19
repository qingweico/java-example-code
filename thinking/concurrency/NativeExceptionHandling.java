package thinking.concurrency;

import thread.pool.ThreadPoolBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static util.Print.print;

/**
 * @author zqw
 * @date 2021/4/9
 * {ThrowsException}
 */
class NativeExceptionHandling {

   public static void main(String[] args) {
      try {
         ExecutorService pool = ThreadPoolBuilder.custom().builder();
         pool.execute(new ExceptionThread());
      }catch (RuntimeException ue){
         // This statement will NOT execute!
         print("Exception has been handled");
      }
   }
}

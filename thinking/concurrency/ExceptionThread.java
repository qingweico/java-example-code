package thinking.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static util.Print.print;

/**
 * Catch exceptions
 *
 * @author:周庆伟
 * @date: 2021/1/18
 */
public class ExceptionThread implements Runnable{
    @Override
    public void run() {
        throw new RuntimeException();
    }

    public static void main(String[] args) {
        try {
            ExecutorService exec = Executors.newCachedThreadPool();
            exec.execute(new ExceptionThread());
        }catch (Exception e){
            print("Exception has been handled");
        }
    }
}

package exception;

/**
 * throw e
 * fillInStackTrace
 *
 * @author:qiming
 * @date: 2021/1/6
 */
// Rethrowing an exception throws the exception to the exception handler in the previous
// environment, and subsequent catch clauses of the same try block are ignored, in addition,
// all information about the exception object is saved, so a handler that catches the exception
// in a higher-level environment can retrieve all information from the exception object.

public class RethrowException {
    public static void f() throws Exception {
        System.out.println("originating the exception in f()");
        throw new Exception("thrown from f()");
    }
    public static void g() throws Exception {
        try {
            f();
        }catch (Exception e){
            System.out.println("Inside g(), e.printStackTrack()");
            e.printStackTrace(System.out);
            throw e;
        }
    }
    public static void h() throws Exception {
        try {
            f();
        }catch (Exception e){
            System.out.println("Inside h(), e.printStackTrace()");
            e.printStackTrace(System.out);

            // If you simply rethrow the current exception object, the printStackTracer()
            // method displays the call stack information of the original exception throw point,
            // not the rethrow point information. To update this information, we can call the
            // fillInStackTrace () method, which returns a Throwable object created by filling
            // the original exception object with the current call stack information
            // in other words, the line that calls fillInStackTrace () is the new exception occurrence.
            throw (Exception) e.fillInStackTrace();
        }
    }
    public static void main(String[] args) {
        try {
            g();
        }catch (Exception e){
            System.out.println("main: printStackTrace()");
            e.printStackTrace(System.out);
        }
        System.out.println("--------------------");

        try {
            h();
        }catch (Exception e){
            System.out.println("main: printStackTrace()");
            e.printStackTrace(System.out);;
        }

    }
}

/**
 * The effect of catching an exception and then rethrowing
 * another one is similar to using fillInStackTrace().
 */
class OneException extends Exception{
    public OneException(String message){super(message);}
}
class TwoException extends Exception{
    public TwoException(String message){super(message);}
}
class RethrowNew{
    public static void f() throws OneException{
        System.out.println("originating the exception in f()");
        throw new OneException("throw from f()");
    }

    public static void main(String[] args) {
        try {
            try {
                f();
            }catch (OneException e){
                System.out.println("Caught in inner try. e.printStackTrace()");
                e.printStackTrace(System.out);
                throw new TwoException("from inner try");
            }
        }catch (TwoException e){
            System.out.println("Caught in outer try. e.printStackTrace()");
            // You just know that you're from main, I don't know anything about f().
            e.printStackTrace(System.out);
        }
    }
}

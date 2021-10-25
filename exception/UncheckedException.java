package exception;

/**
 * UncheckedException
 * <p>
 * Please keep in mind: Exceptions of type RuntimeException(and its subclasses) can only
 * be programmatically ignored, the handling of other types of exceptions is enforced by
 * the compiler.
 *
 * @author:qiming
 * @date: 2021/1/8
 */
public class UncheckedException {
    static void f() {

        // For this type of exception, the compiler does not require an exception specification
        // just like 'static void f() throw Exception{}'.
        // If the RuntimeException is not caught and goes straight to the main method, then the
        // program will call the printStackTrace method of the exception before exiting.
        throw new RuntimeException("From the f()");
    }

    static void g() {
        f();
    }

    public static void main(String[] args) {
        g();
    }
}

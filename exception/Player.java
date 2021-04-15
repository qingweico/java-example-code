package exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;

/**
 * Exception: checked exception: An exception that is forced to be checked at compile time.
 *            unchecked exception: The exception inherited from RuntimeException and This
 *            exception is an error and will be caught automatically.
 *
 * @author:qiming
 * @date: 2020/10/13
 */

public class Player {
    /**
     * java.util.logger
     */
    private static final Logger logger = Logger.getLogger("NoThisSoundException");
    private static final int MAX_RANGE = 10;
    public void play(int index) {
        if (index > MAX_RANGE) {
            try {
                throw new NoThisSoundException("The currently playing song does not exist!");
            } catch (NoThisSoundException e) {

                // This method will prints the sequence of method calls from the method call
                // until the exception is thrown, by default, the information is output to the
                // standard error stream, that is to say e.printStackTrace() is equal to
                // e.printStackTrace(System.err), so suppose you want to send an error to
                // the standard output stream, please use the e.printStackTrace(System.out).
                // It is usually better to send an error to a standard error stream than to
                // a standard output stream, because the latter may be redirected.
                e.printStackTrace(System.out);

                logger.warning(e.getMessage());

                // e.printStackTrace() does not produce a string by default,An overloaded
                // e.printStackTrace() method can be used.
                StringWriter trace = new StringWriter();
                e.printStackTrace(new PrintWriter(trace));
                logger.warning(trace.toString());

                // StackTraceElement[], this method returns an array of the elements in the stack
                // trace, each of which represents a frame in the stack, element 0 is the top of the
                // stack element and is the last method call in the call sequence(The place where the
                // Throwable was created and thrown), instead, the last element in the array and the
                // bottom of the stack is the first method call in the call sequence.
                StackTraceElement[] stackTrace = e.getStackTrace();
                for(StackTraceElement stackTraceElement : stackTrace){
                    System.out.println(stackTraceElement);
                }

                System.out.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new Player().play(11);
    }
}

class NoThisSoundException extends Exception {
    public NoThisSoundException() {
        super();
    }


    public NoThisSoundException(String message) {
        super(message);
    }
}

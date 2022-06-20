package thinking.exception;

import java.io.FileNotFoundException;
import java.io.IOException;

import static util.Print.print;

/**
 * Converts checked exceptions to unchecked exceptions
 *
 * @author:qiming
 * @date: 2021/2/7
 */
// Throwing an exception from main() is convenient, but this is not a generic approach,
// you can do this by wrapping the checked exception inside a RuntimeException, just
// like this:
//    try{
//         // to do something useful
//       }catch (IDontKnowWhatTODoWithThisCheckedException e) {
//            throw new RuntimeException(e);
//       }


public class TurnOffChecking {
    public static void main(String[] args) {
        WrapCheckedException wce = new WrapCheckedException();
        // You can call throwRuntimeException() without a try block, and let
        // RuntimeExceptions leave the method:
        wce.throwRuntimeException(3);

        // Or, you can choose to catch exception:
        for (int i = 0; i < 4; i++) {
            try {
                if (i < 3) {
                    wce.throwRuntimeException(i);
                } else {
                    throw new SomeOtherException();
                }
            } catch (SomeOtherException e) {
                print("SomeOtherException: " + e);
            } catch (RuntimeException re) {
                try {
                    throw re.getCause();
                } catch (FileNotFoundException e) {
                    print("FileNotFoundException: " + e);
                } catch (IOException e) {
                    print("IOException: " + e);
                } catch (Throwable t) {
                    print("Throwable: " + t);
                }
            }
        }
    }
}

class WrapCheckedException {
    void throwRuntimeException(int type) {
        try {
            switch (type) {
                case 0:
                    throw new FileNotFoundException();
                case 1:
                    throw new IOException();
                case 2:
                    throw new RuntimeException("where am I?");
                default:
            }
        } catch (Exception ex) {
            // Adapt to unchecked:
            throw new RuntimeException(ex);
        }
    }
}

class SomeOtherException extends Exception {
}


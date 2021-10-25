package exception;

import static util.Print.print;

/**
 * Finally
 *
 * What does finally do?
 * The finally clause is used when resources other
 * than memory are restored to their initial state.
 *
 * @author:qiming
 * @date: 2021/1/17
 */
public class WithFinally {
    private static final Switch sw = new Switch();

    public static void f() throws OnOffException1, OnOffException2 {
    }

    public static void main(String[] args) {
        try {
            sw.on();
            f();
        } catch (OnOffException1 | OnOffException2 e) {
            print("OnOffException1 or OnOffException2...");
        } finally {
            // Ensure that the sw.off() method is executed in any case.
            sw.off();
        }
    }

}

class OnOffSwitch {
    private static final Switch sw = new Switch();

    public static void f() throws OnOffException1, OnOffException2 {
    }

    public static void main(String[] args) {
        try {
            sw.on();
            f();
            sw.off();
        } catch (OnOffException1 | OnOffException2 e) {
            print("OnOffException1 or OnOffException2...");
            sw.off();
        }
    }
}

class Switch {
    private boolean state = false;

    public boolean read() {
        return state;
    }

    public void on() {
        state = true;
        System.out.println(this);
    }

    public void off() {
        state = false;
        System.out.println(this);
    }

    public String toString() {
        return state ? "on" : "off";
    }
}

class OnOffException1 extends Exception {
}

class OnOffException2 extends Exception {
}

class FourException extends Exception {
}

// Even in cases where the exception is not caught by the current exception handler,
// the exception-handling mechanism executes the finally clause before jumping to a
// higher level of exception handler.
class AlwaysFinally {
    public static void main(String[] args) {
        print("Enter first try block");
        try {
            try {
                print("Enter second try block");
                throw new FourException();
            } finally {
                print("finally in 2nd try block");
            }
        } catch (FourException e) {
            print("Caught FourException in 1st try block");
        } finally {
            print("finally in 1st try block");
        }
    }
}


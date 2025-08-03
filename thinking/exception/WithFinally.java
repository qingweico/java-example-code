package thinking.exception;

import cn.qingweico.io.Print;

/**
 * Finally
 * What does finally do?
 * The `finally clause` is used when resources other
 * than memory are restored to their initial state.
 *
 * @author zqw
 * @date 2021/1/17
 */
public class WithFinally {
    private static final Switch SW = new Switch();

    public static void f() throws OnOff1Exception, OnOff2Exception {
    }

    public static void main(String[] args) {
        try {
            SW.on();
            f();
        } catch (OnOff1Exception | OnOff2Exception e) {
            Print.println("OnOffException1 or OnOffException2...");
        } finally {
            // Ensure that the sw.off() method is executed in any case.
            SW.off();
        }
    }

}

class OnOffSwitch {
    private static final Switch SW = new Switch();

    public static void f() throws OnOff1Exception, OnOff2Exception {
    }

    public static void main(String[] args) {
        try {
            SW.on();
            f();
            SW.off();
        } catch (OnOff1Exception | OnOff2Exception e) {
            Print.println("OnOffException1 or OnOffException2...");
            SW.off();
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

    @Override
    public String toString() {
        return state ? "on" : "off";
    }
}

class OnOff1Exception extends Exception {
}

class OnOff2Exception extends Exception {
}

class FourException extends Exception {
}

// Even in cases where the exception is not caught by the current exception handler,
// the exception-handling mechanism executes the `finally clause` before jumping to a
// higher level of exception handler.

class AlwaysFinally {
    public static void main(String[] args) {
        Print.println("Enter first try block");
        try {
            try {
                Print.println("Enter second try block");
                throw new FourException();
            } finally {
                Print.println("finally in 2nd try block");
            }
        } catch (FourException e) {
            Print.println("Caught FourException in 1st try block");
        } finally {
            Print.println("finally in 1st try block");
        }
    }
}


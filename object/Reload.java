package object;

import static util.Print.print;

/**
 * Method overloading
 *
 * @author zqw
 * @date 2020/12/12
 */
public class Reload {
    public static void main(String[] args) {
        // Output "String method"
        // Because null is not an object and the compiler selects the
        // most specific argument to invoke the overloaded method.
        method((String) null);
        // The main method could be reloaded in java but must be invoked in main(String[] args).
        main("args");
    }

    public static void main(String args) {
        print("Override main " + args);
    }

    public static void method(Object o) {
        print("Object method");
    }

    public static void method(String s) {
        print("String method");
    }

    /**
     * However, when an overloaded method contains both Integer and String arguments, the compiler
     * fails, because both String and Integer are concrete types, and the compiler cannot make a choice,
     * so it needs to be seen as having made the choice(For example, cast using Integer).
     * @param i {@code i}
     */
    public static void method(Integer i) {
        print("Integer method");
    }
}
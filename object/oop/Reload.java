package object.oop;

import cn.qingweico.io.Print;

/**
 * Method overloading
 *
 * @author zqw
 * @date 2020/12/12
 */
class Reload {
    public static void main(String[] args) {
        // Output "String method"
        // Because null is not an object, and the compiler selects the
        // most specific argument to invoke the overloaded method.
        method((String) null);
        // The main method could be reloaded in java but must be invoked in main(String[] args).
        main("args");
    }

    public static void main(String args) {
        Print.println("Override main: " + args);
    }

    public static void method(Object o) {
        Print.println("Object method: " + o);
    }

    public static void method(String s) {
        Print.println("String method: " + s);
    }

    /**
     * However, when an overloaded method contains both Integer and String arguments, the compiler
     * fails. Both String and Integer are concrete types, and the compiler cannot make a choice,
     * so it needs to be seen as having made the choice (For example, cast using Integer).
     *
     * @param i {@code Integer}
     */
    public static void method(Integer i) {
        Print.println("Integer method: " + i);
    }
}

package util;

import java.io.PrintStream;

/**
 * @author:qiming
 * @date: 2021/1/17
 */
public class Print {
    // Print with a newline:
    public static void print(Object obj) {
        System.out.println(obj);
    }

    // Print a newline by itself:
    public static void print() {
        System.out.println();
    }

    // Print with no line break:
    public static void printnb(Object obj) {
        System.out.print(obj);
    }

    // Print with spaces:
    public static void prints(Object o) {
        System.out.print(o + " ");
    }

    // Standard err stream:
    public static void err(Object o) {
        System.err.println(o);
    }

    // exit
    public static void exit(int status) {
        System.exit(status);
    }

    // The new Java SE5 printf() (from C):
    public static PrintStream
    printf(String format, Object... args) {
        return System.out.printf(format, args);
    }
}

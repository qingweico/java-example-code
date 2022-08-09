package util;

import java.io.PrintStream;
import java.util.Map;
import java.util.Set;

/**
 * @author zqw
 * @date 2021/1/17
 */
public class Print {
    /**
     * Print with a newline
     *
     * @param obj The {@code Object} to be printed
     */
    public static void print(Object obj) {
        System.out.println(obj);
    }

    /**
     * Print a newline by itself
     */
    public static void print() {
        System.out.println();
    }

    /**
     * Print with no line break
     *
     * @param obj The {@code Object} to be printed
     */
    public static void printnb(Object obj) {
        System.out.print(obj);
    }

    /**
     * Print with spaces
     *
     * @param o The {@code String} to be printed
     */
    public static void prints(Object o) {
        System.out.print(o + " ");
    }

    /**
     * Standard err stream
     *
     * @param o The {@code Object} to be printed
     */
    public static void err(Object o) {
        System.err.println(o);
    }

    /**
     * Exit
     *
     * @param status exit status
     */
    public static void exit(int status) {
        System.exit(status);
    }

    /**
     * The new Java SE5 printf() (from C)
     *
     * @param format Format string syntax
     * @param args   Arguments referenced by the format specifiers in the format string.
     * @return This output stream
     */
    public static PrintStream
    printf(String format, Object... args) {
        return System.out.printf(format, args);
    }

    /**
     * Print a Map
     * @param map the map to print
     */
    public static void toPrint(Map<?, ?> map) {
        if (map == null || map.size() == 0) {
            System.err.println("map is null or size == 0");
            return;
        }
        Set<? extends Map.Entry<?, ?>> entrySet = map.entrySet();
        for (Map.Entry<?, ?> entry : entrySet) {
            System.out.printf("[key: %s, value: %s]%n", entry.getKey(), entry.getValue());
        }
    }
}

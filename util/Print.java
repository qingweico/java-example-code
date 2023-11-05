package util;

import util.constants.Constants;
import util.constants.Symbol;

import javax.annotation.Nonnull;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collection;
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
    public static void println(Object obj) {
        System.out.println(obj);
    }

    /**
     * Print a newline by itself
     */
    public static void println() {
        System.out.println();
    }

    /**
     * Print with no line break
     *
     * @param obj The {@code Object} to be printed
     */
    public static void print(Object obj) {
        System.out.print(obj);
    }

    /**
     * Print with spaces
     *
     * @param o The {@code String} to be printed
     */
    public static void prints(Object o) {
        System.out.print(o + Symbol.WHITE_SPACE);
    }

    /**
     * Print the spaces
     */
    public static void prints() {
        System.out.print(Symbol.WHITE_SPACE);
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
     * Standard err stream [K : V]
     *
     * @param arg   k
     * @param value v
     */
    public static void err(Object arg, Object value) {
        System.err.printf("%s %s %s%n", arg, Symbol.COLON, value);
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
    public static PrintStream printf(String format, Object... args) {
        try (PrintStream printStream = System.out.printf(format, args)) {
            printStream.flush();
            return printStream;
        }
    }

    /**
     * Formatting and printing the cost time
     *
     * @param time long time
     */
    public static void time(String title, long time) {
        try (PrintStream printStream = printf("%s time %s %d %s%n", title, Symbol.COLON, time, Constants.MS)) {
            printStream.flush();
        }
    }

    /**
     * Print a Map
     *
     * @param map the map to print
     */
    public static void toPrint(Map<?, ?> map) {
        if (map == null || map.size() == 0) {
            System.err.println("map is null or size == 0");
            return;
        }
        Set<? extends Map.Entry<?, ?>> entrySet = map.entrySet();
        for (Map.Entry<?, ?> entry : entrySet) {
            System.out.printf("[key %s %s%s value %s %s]%n", Symbol.COLON, entry.getKey(), Symbol.COMMA, Symbol.COLON, entry.getValue());
        }
    }

    /**
     * To print collection
     *
     * @param c ? extends Collection<?>
     */
    public static void toPrint(Collection<?> c) {
        for (Object o : c) {
            System.out.println(o);
        }
    }

    /**
     * Print Gracefully  k : v
     *
     * @param arg   k
     * @param value v
     */
    public static void grace(Object arg, Object value) {
        System.out.printf("%s %s %s%n", arg, Symbol.COLON, value);
    }

    /**
     * 打印数组中的元素
     *
     * @param e 数组 e
     */
    public static <T> void printArray(T[] e, boolean isNewLine) {
        if (isNewLine) {
            for (T t : e) {
                println(t);
            }
        } else {
            println(Arrays.toString(e));
        }
    }

    /**
     * 打印数组中的元素 (不换行)
     *
     * @param e 数组 e
     */
    public static <T> void printArray(T[] e) {
        printArray(e, false);
    }

    /**
     * System Logger {@since JDK9} {@link java.lang.System.Logger} 默认实现 {@link java.util.logging.Logger}
     * JUL {@link java.util.logging} {@since JDK1.4}
     * <p>
     * 打印日志
     *
     * @param name     the name of the logger
     * @param logLevel 日志级别
     * @param message  打印信息
     */
    public static void log(@Nonnull String name, System.Logger.Level logLevel, String message) {
        System.getLogger(name).log(logLevel, message);
    }
}

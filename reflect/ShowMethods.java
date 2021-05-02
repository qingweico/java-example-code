package reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 * @author:qiming
 * @date: 2020/12/1
 */

public class ShowMethods {
    private static final Pattern p = Pattern.compile("\\w+\\.");

    public static void main(String[] args) {
        if (args.length < 1) {
            String usage = "usage: \n" +
                    "ShowMethods qualified.class.name\n" +
                    "To show all methods in class or: \n" +
                    "ShowMethods qualified.class.name word\n" +
                    "To search for methods involving 'word' ";
            System.out.println(usage);
            System.exit(0);
        }
        int lines = 0;
        try {
            Class<?> c = Class.forName(args[0]);
            Method[] methods = c.getMethods();
            Constructor<?>[] constructors = c.getConstructors();
            if (args.length == 1) {
                for (Method method : methods) {
                    System.out.println(p.matcher(method.toString()).replaceAll(""));
                }
                for (Constructor<?> constructor : constructors) {
                    System.out.println(p.matcher(constructor.toString()).replaceAll(""));
                }
                lines = methods.length + constructors.length;
            } else {
                for (Method method : methods) {
                    if (method.toString().contains(args[1])) {
                        System.out.println(p.matcher(method.toString()).replaceAll(""));
                        lines++;
                    }
                }
                for (Constructor<?> constructor : constructors) {
                    if (constructor.toString().contains(args[1])) {
                        System.out.println(p.matcher(constructor.toString()).replaceAll(""));
                        lines++;
                    }
                }
            }
            System.out.println(lines);
        } catch (ClassNotFoundException e) {
            System.out.println("No such class " + e.getMessage());
        }
    }

    // Usage: input 'java ShowMethods.java reflect.ShowMethods' in terminal
}
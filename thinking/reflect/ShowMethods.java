package thinking.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 * @author zqw
 * @date 2020/12/1
 */
class ShowMethods {
    private static final Pattern P = Pattern.compile("\\w+\\.");

    public static void main(String[] args) {
        if (args.length < 1) {
            String usage = """
                    usage:\s
                    ShowMethods qualified.class.name
                    To show all methods in class or:\s
                    ShowMethods qualified.class.name word
                    To search for methods involving 'word'\s""";
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
                    System.out.println(P.matcher(method.toString()).replaceAll(""));
                }
                for (Constructor<?> constructor : constructors) {
                    System.out.println(P.matcher(constructor.toString()).replaceAll(""));
                }
                lines = methods.length + constructors.length;
            } else {
                for (Method method : methods) {
                    if (method.toString().contains(args[1])) {
                        System.out.println(P.matcher(method.toString()).replaceAll(""));
                        lines++;
                    }
                }
                for (Constructor<?> constructor : constructors) {
                    if (constructor.toString().contains(args[1])) {
                        System.out.println(P.matcher(constructor.toString()).replaceAll(""));
                        lines++;
                    }
                }
            }
            System.out.println(lines);
        } catch (ClassNotFoundException e) {
            System.out.println("No such class " + e.getMessage());
        }
    }

    // Program arguments: `thinking.reflect.ShowMethods`
}
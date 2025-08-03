package effective;

import cn.qingweico.io.Print;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Set;

import static cn.qingweico.io.Print.exit;

/**
 * 接口优先于反射机制
 *
 * @author zqw
 * @date 2021/11/6
 */
class Article65 {
    public static void main(String[] args) {
        // Program arguments: java.util.TreeSet xxx ...
        if(args[0] == null) {
            throw new IllegalArgumentException("args null!");
        }
        // Translate the class name into a Class object
        Class<? extends Set<String>> cls = null;
        try {
            cls = (Class<? extends Set<String>>) Class.forName(args[0]);
        } catch (ClassNotFoundException e) {
            fatalError("Class not found");
        }
        // Get the construct
        Constructor<? extends Set<String>> cons = null;
        try {
            cons = cls.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            fatalError("No parameterless constructor");
        }

        // Instantiate the set
        Set<String> s = null;
        try {
            s = cons.newInstance();
        } catch (IllegalAccessException e) {
            fatalError("Constructor not accessible");
        } catch (InvocationTargetException e) {
            fatalError("Constructor threw " + e.getCause());
        } catch (InstantiationException e) {
            fatalError("Class not instantiable");
        } catch (ClassCastException e) {
            fatalError("Class doesn't implements Set");
        }
        // Exercise the set
        s.addAll(Arrays.asList(args).subList(1, args.length));
        Print.println(s);

        // A superclass of various reflection exception since JDK 7
        // ReflectiveOperationException

    }

    private static void fatalError(String msg) {
        Print.println(msg);
        exit(1);
    }
}

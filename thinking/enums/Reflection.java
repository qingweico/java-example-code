package thinking.enums;

import util.Print;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static util.Print.println;

/**
 * @author zqw
 * @date 2020/10/18
 */
public class Reflection {
    public static Set<String> analyse(Class<?> enumClass) {
        Print.println("-----Analysing-----" + enumClass + "-----");
        Print.println("interface : ");
        for (Type t : enumClass.getGenericInterfaces()) {
            Print.println("\t");
            Print.println(t);
        }
        Print.println("Base : " + enumClass.getSuperclass());
        Print.println("Method : ");
        HashSet<String> methods = new HashSet<>();
        for (Method m : enumClass.getMethods()) {
            methods.add(m.getName());
        }
        Print.println(methods);
        return methods;
    }


    public static void main(String[] args) {
        Set<String> exploreMethods = analyse(Explore.class);
        Set<String> enumMethods = analyse(Enum.class);
        Print.println(exploreMethods.containsAll(enumMethods));
        Print.println(exploreMethods.removeAll(enumMethods));
        Print.println(exploreMethods);
    }
}

enum Search {
    /**
     * hither
     */
    HITHER,
    /**
     * you
     */
    YON
}

class UpcastEnum {

    public static void main(String[] args) {
        // It could compile
        Search[] values = Search.values();
        // [HITHER, YON]
        System.out.println(Arrays.toString(values));
        Enum<Search> s = Search.HITHER;
        // [s.values();] Won't compile 因为 Enum 中看没有values方法

        for (Enum<?> en : s.getClass().getEnumConstants()) {
            Print.println(en);
        }
        println();
    }
}

enum Explore {
    /**
     * here
     */
    HERE,
    /**
     * there
     */
    THERE
}


package enums;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

/**
 * @author:qiming
 * @date: 2020/10/18
 */

enum Explore {
    HERE, THERE
}

public class Reflection {
    public static Set<String> analyse(Class<?> enumClass) {
        System.out.println("-----Analysing-----" + enumClass + "-----");
        System.out.println("interface : ");
        for (Type t : enumClass.getGenericInterfaces()) {
            System.out.print("\t");
            System.out.println(t);
        }
        System.out.println("Base : " + enumClass.getSuperclass());
        System.out.println("Method : ");
        HashSet<String> methods = new HashSet<>();
        for (Method m : enumClass.getMethods()) {
            methods.add(m.getName());
        }
        System.out.println(methods);
        return methods;
    }

    //
    public static void main(String[] args) {
        Set<String> exploreMethods = analyse(Explore.class);
        Set<String> enumMethods = analyse(Enum.class);
        System.out.println(exploreMethods.contains(enumMethods));
        System.out.println(exploreMethods.removeAll(enumMethods));
        System.out.println(exploreMethods);
    }
}

enum Search {
    HITHER, YON
}

class UpcastEnum {
    //
    public static void main(String[] args) {
        // It could compile
        Search[] values = Search.values();
        Enum<Search> s = Search.HITHER;
        // Won't compile
        // s.values();
        //
        for (Enum en : s.getClass().getEnumConstants()) {
            System.out.println(en);
        }
        System.out.println();
    }
}

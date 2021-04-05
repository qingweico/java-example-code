package enums;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

import static util.Print.print;

/**
 * @author:qiming
 * @date: 2020/10/18
 */

enum Explore {
    HERE, THERE
}

public class Reflection {
    public static Set<String> analyse(Class<?> enumClass) {
        print("-----Analysing-----" + enumClass + "-----");
        print("interface : ");
        for (Type t : enumClass.getGenericInterfaces()) {
            print("\t");
            print(t);
        }
        print("Base : " + enumClass.getSuperclass());
        print("Method : ");
        HashSet<String> methods = new HashSet<>();
        for (Method m : enumClass.getMethods()) {
            methods.add(m.getName());
        }
        print(methods);
        return methods;
    }


    public static void main(String[] args) {
        Set<String> exploreMethods = analyse(Explore.class);
        Set<String> enumMethods = analyse(Enum.class);
        print(exploreMethods.contains(enumMethods));
        print(exploreMethods.removeAll(enumMethods));
        print(exploreMethods);
    }
}

enum Search {
    HITHER, YON
}

class UpcastEnum {

    public static void main(String[] args) {
        // It could compile
        Search[] values = Search.values();
        Enum<Search> s = Search.HITHER;
        // Won't compile
        // s.values();
        //
        for (Enum en : s.getClass().getEnumConstants()) {
            print(en);
        }
        print();
    }
}

package thinking.enums;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static util.Print.print;

/**
 * @author zqw
 * @date 2020/10/18
 */
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
        print(exploreMethods.containsAll(enumMethods));
        print(exploreMethods.removeAll(enumMethods));
        print(exploreMethods);
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
            print(en);
        }
        print();
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


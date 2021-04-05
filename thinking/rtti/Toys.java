package thinking.rtti;

import java.lang.reflect.InvocationTargetException;

/**
 * @author:qiming
 * @date: 2021/1/16
 */
public class Toys {
    static void printInfo(Class<?> cc) {
        System.out.println("Class name" + cc.getName() + "is interface ?" +
                " [" + cc.isInterface() + "]");
        // The getSimpleName() method returns class name without a package name.
        System.out.println("Simple name: " + cc.getSimpleName());
        // The getCanonicalName() method returns the fully qualified class name.
        System.out.println("Canonical name: " + cc.getCanonicalName());
    }

    public static void main(String[] args) {
        Class c = null;
        try {
            c = Class.forName("thinking.rtti.FancyToy");
        } catch (ClassNotFoundException e) {
            System.out.println("can't find FancyToy");
            System.exit(1);
        }
        printInfo(c);
        // The getInterfaces() method returns the interfaces contained within the Class object.
        for (Class face : c.getInterfaces()) {
            printInfo(face);
        }
        Class up = c.getSuperclass();
        Object obj = null;
        try {
            // Please attention: Classes created using newInstance must have a default
            // and public constructor(package-private can't).
            obj = up.getConstructor().newInstance();
        } catch (InstantiationException e) {
            System.out.println("Cannot instantiate");
            System.exit(1);
        } catch (IllegalAccessException e) {
            System.out.println("Cannot access");
            System.exit(1);
        } catch (NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        printInfo(obj.getClass());
    }
}

interface HasBatteries {
}

interface Waterproof {
}

interface Shoots {
}

class Toy {
    public Toy() {
    }

    public Toy(int i) {
    }
}

class FancyToy extends Toy implements HasBatteries, Waterproof, Shoots {
    FancyToy() {
        super(1);
    }

}

package thinking.rtti;

import java.util.Random;

import static util.Print.print;

/**
 *
 * @author zqw
 * @date 2021/1/17
 */
public class ClassInitialization {
    public static Random random = new Random(47);


    public static void main(String[] args) throws ClassNotFoundException {
        // Using .class to create a reference to a Class object does not automatically
        // initialize the Class object.
        Class<Inimitable> inimitable = Inimitable.class;
        print("After creating Inimitable ref:" + inimitable.getCanonicalName());

        // Does not trigger Initializing:
        // StaticFinal is a compiler constant that can be read without initializing the class
        print(Inimitable.STATIC_FINAL);

        // Does trigger Initializing:
        // Access to the staticFinal0 variable will force the initialization of the class
        // because it is not a compile-time constant.
        print(Inimitable.STATIC_FINAL0);


        // Does trigger Initializing:
        print(Inimitable0.staticNoFinal);
        Class<?> inimitable2 = Class.forName("thinking.rtti.Inimitable2");
        print("After creating Inimitable2 ref: " + inimitable2.getCanonicalName());
        print(Inimitable2.staticNoFinal);


    }
}

class Inimitable {
    static final int STATIC_FINAL = 47;
    static final int STATIC_FINAL0 =
            ClassInitialization.random.nextInt(1000);

    static {
        System.out.println("Initializing Inimitable");
    }
}

class Inimitable0 {
    static int staticNoFinal = 147;

    static {
        System.out.println("Initializing Inimitable0");
    }
}

class Inimitable2 {
    static int staticNoFinal = 74;

    static {
        System.out.println("Initializing Inimitable2");
    }
}




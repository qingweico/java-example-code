package thinking;

import java.lang.reflect.InvocationTargetException;

class A {

}

class B extends A {

}

/**
 * @author:qiming
 */
public class ClassType {
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<B> BClass = B.class;
        Class<? super B> AClass = BClass.getSuperclass();
        // This won't compile
        // Class<A> AClass = BClass.getSuperclass();
        // only produces object
        Object a = AClass.getConstructor().newInstance();
        System.out.println(a);


    }
}


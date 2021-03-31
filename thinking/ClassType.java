package thinking;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

class A {

}

class B extends A {

}

/**
 * @author 周庆伟
 */
public class ClassType {
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<B> BClass = B.class;
        Class<? super B> AClass = BClass.getSuperclass();
        //This won't compile
        //Class<A> AClass = BClass.getSuperclass();
        //only produces object
        Object a = AClass.getConstructor().newInstance();
        System.out.println(a);


    }
}


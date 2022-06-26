package thinking.oop;

import java.lang.reflect.InvocationTargetException;


/**
 * @author zqw
 * @date 2020/09/13
 */
public class ClassType {
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<Integer> integerClass = Integer.class;
        // return <? super T>
        Class<? super Integer> ics = integerClass.getSuperclass();

        // only produces object
        // Number is abstract
        Object a = ics.getConstructor().newInstance();
        System.out.println(a);


    }
}


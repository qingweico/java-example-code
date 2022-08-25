package effective;

import java.util.*;

/**
 * 请不要使用原生态类型
 *
 * <p>
 * 参数化的类型      List<String>
 * 实际类型参数      String
 * 泛型            List<E>
 * 形式类型参数      E
 * 无限制通配符类型  List<?>
 * 上界通配符  <? extend Number>
 * 下界通配符  <? super Number>
 * 原生态类型       List
 * 有限制类型参数   <E extends Number>
 * 递归类型限制     <T extends Comparable<T>>
 * 有限制通配符类型  List<E extends Number>
 * 泛型方法        static <E> List<E> asList(E[] a)
 * 令牌类型        String.class
 *
 * @author zqw
 * @date 2020/12/15
 */
// Each generic defines a primitive type, that is, a generic name without any actual type arguments.
// List<E> --> List
// Primitive type exist to be compatible with code that predates generics.

class Article26 {
     public static void main(String[] args) {
        List<String> string = new ArrayList<>();
        // Generics have rules for subtyping:
        // List<String> is a subtype of primitive type List,
        // not a subtype of parameterized type List<Object>.
        unsafeAdd(string, Integer.valueOf(42));
        // It could be compiled but an exception to ClassCastException is thrown at runtime.
        // Has compile-generated cast
        String s = string.get(0);

    }


    private static void unsafeAdd(List list, Object o) {
        list.add(o);
    }

    // Use of raw type for unknown element type - don't do this!

    static int numElementsInCommon(Set s1, Set s2) {
        int result = 0;
        for (Object o1 : s1) {
            if (s2.contains(o1)) {
                result++;
            }
        }
        return result;
    }
    // Set<?>: a collection of some type
    // Using primitive type is danger, the safe alternative is to use unrestricted wildcards.
    // Using unbounded wildcard type - typesafe and flexible.
    // static int numElementsInCommon(Set<?> s1, Set<?> s2){...}

    // What's the difference between unrestricted wildcard types and primitive types?
    // The former is safe, the latter unsafe.
    // You can put any element into a collection that uses primitive types, but
    // you cannot put any elements (except null) into Collection<?>.

    // The primitive type must be used in the class literal.
    // List.class String[].class int.class is valid, but List<String.class>
    // and List<?>.class is not valid.


    // Legitimate use of raw type - instanceof operator

    public static void rawType(Object o) {
        // illegal!
        // Because the generic information is erased at run time.
        // o instance Set<String>

        // Raw type
        if (o instanceof Set) {
            // Replace primitive types with unrestricted wildcard types,
            // this is a checked transformation,
            // so it does not cause compile-time warnings.

            // Wildcard type
            Set<?> s = (Set<?>) o;

            // Set s = (Set)o;
        }
    }


    // Generics have subtyping rules:
    // List<String> is a subtype of the primitive type List, rather than the
    // parameterized type List<Object>.


    // Set is a primitive type, separated from the generic system(unsafe).
    // Set<Object> is a parameterized type that represents a collection
    // that can contain any object type(safe).
    // Set<?> is a wildcard type that represents a collection that can
    // contain only some unknown object type(safe).


}

class SuperWildCard {
    public static void main(String[] args) {

        // Although Integer is a subclass of Number, List<Integer> is not a subclass of List<Number>.
        // Method parameters take all generic types, such as Integer or Integer parent classes such as
        // Object Number.
        LinkedList<? super Integer> linkedList = new LinkedList<>();

        // An Integer can be passed in as a value.
        // Want to just insert it, not take it out, use it<? super>.
        linkedList.add(3);

        // However, you cannot get a reference to an Integer, you can only accept it using Object.
        // It can't be compiled
        // Integer x = list.getFirst();
        // good
        Object o = linkedList.getFirst();

        List<Integer> li = Arrays.asList(1, 2, 3);
        System.out.println(sumOfList(li));

        List<Double> ld = Arrays.asList(1.2, 2.3, 3.5);
        System.out.println(sumOfList(ld));


    }

    // The return type qualification for which extends is used cannot be used for parameter type qualification.
    // Super can be used for parameter type qualification and cannot be used for return type qualification.


    public static double sumOfList(List<? extends Number> list) {
        double s = 0.0;

        // Want to just take it out, not insert it, use it <? extends>.
        for (Number n : list) {
            s += n.doubleValue();
        }
        return s;
    }
}

package effective;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 列表优于数组
 *
 * @author:qiming
 * @date: 2021/3/5
 */
public class Article28<E> {
    // First, Arrays are covariant, if Sub is a subtype of Super, then the type Sub[] is
    // a subtype of Super[]; In contrast, generics are mutable, and for any two different
    // types, Type1 and Type2, List<Type1> neither List<Type2> subtype of, also not
    // List<Type2> the type of super.
    // eg: Double[] is subtype of Number[].
    private abstract static class N {
        abstract Number[] n();
    }
    static class D extends N {

        @Override
        Double[] n() {
            return null;
        }
    }

    // Second, Arrays are reified, and arrays know and enforce their data types at run time, but
    // Generics are implemented by erasure. Generics only reinforce their type information
    // at compile time and erasure their element type information at run time.


    // The effect of erasure is make generics freely interoperability with code that doesn't
    // use generics to ensure a smooth transition to generics in Java5.


    // generic array creation exception, such as new ArrayList<E>[],
    // new ArrayList<String>[] or new E[]. because it's not type safe!


    public static void main(String[] args) {
        Object[] objectArray = new Long[1];
        // Throws ArrayStoreException
        objectArray[0] = "I don't fit it";

        // Won't compile!
        // List<Object> ol = new ArrayList<Long>(); Incompatible types
        // ol.add("I don't fit it");
    }
}
class ArrayGeneric {
    public static void main(String[] args) {
        // generic array creation
        // List<String>[] stringLists = new List<String>[1];
        // List<Integer> intList = List.of(42);
        // Object[] objects = stringLists;
        //
        // objects[0] = intList;
        // String s =stringLists[0].get(0);


        // legal
         List<?>[] lists = new List<?>[1];
    }
}

// E, List<E>, List<String> be called non-reifiable type:
// A type whose rt representation contains less information
// that its compile-time information.
// The only parameterized types that can be reifiable are unrestricted types,
// such as List<?>, Map<?>




// Chooser - a class badly in need of generic
class Chooser {
    private final Object[] choiceArray;
    public Chooser(Collection choices) {
        this.choiceArray = choices.toArray();
    }
    public Object choose() {
        Random rnd = ThreadLocalRandom.current();
        return choiceArray[rnd.nextInt(choiceArray.length)];
    }
}

// A first cut at making Chooser generic
class ChooserUseGeneric<T> {
    private final T[] choiceArray;
    @SuppressWarnings("unchecked")
    public ChooserUseGeneric(Collection<T> choice) {
        // If the conversion is not enforced, it will not compile!
        this.choiceArray = (T[])choice.toArray();
    }
    public Object choose() {
        Random rnd = ThreadLocalRandom.current();
        return choiceArray[rnd.nextInt(choiceArray.length)];
    }

}
// List-based Chooser - typesafe
class ChooserUseList <T>{
    // To eliminate unchecked exceptions, you must choose to replace arrays with lists.
    private final List<T> choiceList;
    public ChooserUseList(Collection<T> choices) {
        this.choiceList = new ArrayList<>(choices);
    }
    public Object choose() {
        Random rnd = ThreadLocalRandom.current();
        return choiceList.get(rnd.nextInt(choiceList.size()));
    }

    public static void main(String[] args) {
        List<Integer> rs = Arrays.asList(1, 2 ,3);
        new ChooserUseList<>(rs).choose();
    }


}
package thinking.genericity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Unbounded wildcard <?>
 *
 * @author:qiming
 * @date: 2021/1/14
 */
public class UnboundedWildcards {
    static List list1;
    static List list2;
    static List<? extends Object> list3;

    static void assign1(List list) {
        list1 = list;
        list2 = list;
        list3 = list;
    }

    static void assign2(List<?> list) {
        list1 = list;
        list2 = list;
        list3 = list;
    }
    // List expression: 'Holds a native List of any Object type'.
    // List<?> expression: 'A non-native List that has a particular type, but we don't know
    // what that type is'.


    static void assign3(List<? extends Object> list) {
        list1 = list;
        list2 = list;
        list3 = list;
    }

    public static void main(String[] args) {
        assign1(new ArrayList());
        assign2(new ArrayList());
        assign3(new ArrayList());

        assign1(new ArrayList<String>());
        assign2(new ArrayList<String>());
        assign3(new ArrayList<String>());

        List<?> wildList = new ArrayList();
        wildList = new ArrayList<String>();
        assign1(wildList);
        assign2(wildList);
        assign3(wildList);
    }
}

class UnboundedWildcard {
    static Map map1;
    static Map<?, ?> map2;
    static Map<String, ?> map3;

    static void assign1(Map map) {
        map1 = map;
    }

    static void assign2(Map<?, ?> map) {
        map2 = map;
    }

    static void assign3(Map<String, ?> map) {
        map3 = map;
    }

    public static void main(String[] args) {
        assign1(new HashMap());
        assign2(new HashMap());
        assign3(new HashMap());


        assign1(new HashMap<String, Integer>());
        assign2(new HashMap<String, Integer>());
        assign3(new HashMap<String, Integer>());
    }
}

class Wildcards {
    static void rawArgs(Holder holder, Object arg) {
        // Warning: Unchecked call to set(T) as a member of the raw type Holder.
        holder.set(arg);

        // Some warning
        holder.set(new Wildcards());


        // Can't do this don't have any 'T'.
        // T t = holder.get();

    }

    static void unboundedArgs(Holder<?> holder, Object arg) {
        // Error: set(capture of ?) in Holder<capture of ?> cannot be applied to Object.
        // holder.set(arg);

        // Same error
        // holder.set(new Wildcards());

        // OK, but type information has been lost.
        Object obj = holder.get();
    }

    static <T> T exact1(Holder<T> holder) {
        return holder.get();
    }

    static <T> T exact2(Holder<T> holder, T arg) {
        holder.set(arg);
        return holder.get();
    }

    // Whether to use <? extend> depends on whether you want to return a type-determined return value
    // from a generic parameter.

    static <T> T wildSubtype(Holder<? extends T> holder, T arg) {
        // Error: set(capture of ? extend T) in Holder<capture of ? extend T>
        // cannot be applied to T.
        // holder.set(arg);
        return holder.get();
    }

    // Whether to use <? super> depends on whether you want to pass a type-determined parameter to a
    // generic parameter.

    static <T> void wildSupertype(Holder<? super T> holder, T arg) {
        holder.set(arg);

        // Error: incompatible types: found Object, required T
        // T t = holder.get();

        // OK, but type information has been lost.
        Object obj = holder.get();
    }

    public static void main(String[] args) {
        Holder raw = new Holder();

        Holder<Long> qualified = new Holder<>();
        Holder<?> unbounded = new Holder<Long>();
        Holder<? extends Long> bounded = new Holder<Long>();

        Long lng = 1L;

        rawArgs(raw, lng);
        rawArgs(qualified, lng);
        rawArgs(unbounded, lng);
        rawArgs(bounded, lng);

        unboundedArgs(raw, lng);
        unboundedArgs(qualified, lng);
        unboundedArgs(unbounded, lng);
        unboundedArgs(bounded, lng);

        // Warning: Unchecked conversion from Holder to Holder<T>,
        // Unchecked method invocation: exact1(Holder<T>) is applied to (Holder).
        Object r1 = exact1(raw);

        Long r2 = exact1(qualified);

        // Must return Object
        Object r3 = exact1(unbounded);

        Long r4 = exact1(bounded);

        // Warning: Unchecked conversion from Holder to Holder<Long>,
        // Unchecked method invocation: exact2(Holder<T>, T) is applied to (Holder, Long).
        Long r5 = exact2(raw, lng);

        Long r6 = exact2(qualified, lng);


        // Error: exact2(Holder<T>, T) cannot be applied to (Holder<capture of ?>, Long).
        // Long r7 = exact2(unbounded, lng);

        // Error: exact2(Holder<T>, T) cannot be applied to (Holder<capture of ? extend Long>, Long).
        // Long r8 = exact2(bounded, lng);


        // Warning: Unchecked conversion from Holder to Holder<? extend Long>,
        // Unchecked method invocation: wildSubtype(Holder<? extend Long>, T) is applied to (Holder, Long).
        Long r9 = wildSubtype(raw, lng);

        // Ok, but can only return Object
        Object r10 = wildSubtype(unbounded, lng);

        Long r11 = wildSubtype(bounded, lng);

        // Warning: Unchecked conversion from Holder to Holder<? super Long>,
        // Unchecked method invocation: wildSubtype(Holder<? super T>, T) is applied to (Holder, Long).
        wildSupertype(raw, lng);

        wildSupertype(qualified, lng);

        // Error: wildSupertype(Holder<? super T>, T) cannot be applied to (Holder<capture of ?>, Long).
        // wildSupertype(unbounded, lng);


        // Error: Error: wildSupertype(Holder<? super T>, T) cannot be applied to
        // (Holder<capture of ? extend Long>, Long).
        // wildSupertype(bounded, lng);


    }
}

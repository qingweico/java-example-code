package thinking.genericity.wildcard;

import thinking.genericity.Holder;

import static util.Print.print;

/**
 * @author:qiming
 * @date: 2021/1/20
 */
public class CaptureConversion {
    static <T> void f1(Holder<T> holder) {
        T t = holder.get();
        print(t.getClass().getSimpleName());
    }

    static void f2(Holder<?> holder) {
        // Call with capture type
        f1(holder);
    }

    public static void main(String[] args) {
        Holder raw = new Holder<Integer>(1);
        // Produces warning
        f1(raw);
        // No warning
        // The parameter type is captured during the call to f2(), so it can be used
        // in the call to f1().
        f2(raw);
        Holder rawBasic = new Holder();
        // Warning
        rawBasic.set(new Object());
        // No Warning
        f2(rawBasic);
        // Upcast to Holder<?>, still figures it out
        Holder<?> wildcard = new Holder<Double>(1.0);
        f2(wildcard);

    }
}

package thinking.genericity.wildcard;

import thinking.genericity.Holder;
import cn.qingweico.io.Print;

/**
 * 通配符 ?
 *
 * @author zqw
 * @date 2021/1/20
 */
class CaptureConversion {
    static <T> void f1(Holder<T> holder) {
        T t = holder.get();
        Print.println(t.getClass().getSimpleName());
    }

    static void f2(Holder<?> holder) {
        // Call with a capture type
        f1(holder);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void main(String[] args) {
        // 装箱 int -> Integer
        Holder raw = new Holder<>(1);
        // Produce warning
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
        // 装箱 double -> Double
        Holder<?> wildcard = new Holder<>(1.0);
        f2(wildcard);
    }
}

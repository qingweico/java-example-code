package util.klass;

import java.lang.invoke.*;
import java.lang.reflect.Field;
import java.util.function.ToIntFunction;

/**
 * @author zqw
 * @date 2023/4/22
 * VM option : --add-opens=java.base/java.lang.invoke=ALL-UNNAMED
 */
public class AnyToFunctional {
    public static void main(String[] args) throws Throwable {
        Class<MethodHandles.Lookup> lookupClass = MethodHandles.Lookup.class;
        Field implLookup = lookupClass.getDeclaredField("IMPL_LOOKUP");
        implLookup.setAccessible(true);
        MethodHandles.Lookup lookup = (MethodHandles.Lookup)implLookup.get(null);
        MethodHandle coder = lookup
                .in(String.class)
                .findSpecial(
                        String.class,
                        "coder",
                        MethodType.methodType(byte.class),
                        String.class
                );

        CallSite applyAsInt = LambdaMetafactory.metafactory
                (
                    lookup,
                    "applyAsInt",
                    MethodType.methodType(ToIntFunction.class),
                    MethodType.methodType(int.class, Object.class),
                    coder,
                    MethodType.methodType(byte.class, String.class));
        @SuppressWarnings("unchecked")
        ToIntFunction<String> strCoder  = (ToIntFunction<String>) applyAsInt.getTarget().invoke();
        int asInt = strCoder.applyAsInt("String");
        // String 中 coder 属性默认有0 和 1,  0 代表 Latin-1(单字节编码) 1 代表 UTF-16
        System.out.println(asInt);
    }
}

package thinking.array;

import java.util.Arrays;

/**
 * Type check
 *
 * @author zqw
 * @date 2020/10/6
 */
class ParameterizedArrayType {
    public static void main(String[] args) {
        Integer[] ai = {1, 2, 3, 4, 5};
        Double[] ad = {1.1, 2.2, 3.3, 4.4, 5.5};

        ai = new ClassParameter<Integer>().f(ai);
        ad = new ClassParameter<Double>().f(ad);

        System.out.println(Arrays.toString(MethodParameter.f(ai)));
        System.out.println(Arrays.toString(MethodParameter.f(ad)));

    }

}

class ClassParameter<T> {
    public T[] f(T[] args) {
        return args;
    }
}

class MethodParameter {
    public static <T> T[] f(T[] args) {
        return args;
    }
}

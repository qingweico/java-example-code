package effective;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 消除非受检的警告
 * >1 非受检转换警告(unchecked cast warning)
 * >2 非受检方法调用警告
 * >3 非受检参数化可变参数类型警告(unchecked parameterized vararg type warning)
 * >4 非受检转换警告(unchecked conversion warning)
 *
 * @author zqw
 * @date 2021/1/28
 */
public class Article27 {
    /**
     * The size of the ArrayList (the number of elements it contains).
     *
     * @serial
     */
    private int size;
    transient Object[] elementData;

    public static void main(String[] args) {
        // <>: JDK 7 diamond operator
        Set<String> set = new HashSet<>();

    }

    // ArrayList#toArray(T[] a)
    // Adding local variable to reduce scope of @SuppressWarnings
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            // This cast is correct because the array we're creating
            // is of the same type as the one passed in, which is T[].
            @SuppressWarnings("unchecked") T[] result = (T[]) Arrays.copyOf(elementData, size, a.getClass());
            return result;
        }

        System.arraycopy(elementData, 0, a, 0, size);
        if (a.length > size)
            a[size] = null;
        return a;
    }

}

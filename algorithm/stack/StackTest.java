package algorithm.stack;

import cn.qingweico.io.Print;
import cn.qingweico.supplier.Tools;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

import static cn.qingweico.io.Print.printf;

/**
 * @author zqw
 * @date 2021/10/31
 */
public class StackTest {
    @Test
    public void test() {
        stackTest(ArrayStack.class, 1000000);
        stackTest(LinkedListStack.class, 1000000);
    }

    public void stackTest(Class<?> cls, int n) {
        try {
            var constructor = cls.getConstructor();
            var rawInstance = constructor.newInstance();
            @SuppressWarnings("unchecked") var inst = (Stack<Integer>) rawInstance;
            int[] raw = Tools.genList(n, 1000000).stream().mapToInt(x -> x).toArray();
            var start = System.nanoTime();
            for (var i : raw) {
                inst.push(i);
            }
            for (int i = 0; i < n; i++) {
                inst.pop();
            }
            printf(rawInstance.getClass().getSimpleName()
                    + " time: %s\n", (System.nanoTime() - start) / 1_000_000.0 + " ms");
        } catch (NoSuchMethodException
                | InvocationTargetException
                | InstantiationException
                | IllegalAccessException e) {
            Print.err(e.getMessage());
        }
    }
}

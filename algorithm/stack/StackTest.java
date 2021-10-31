package algorithm.stack;

import algorithm.sort.Tools;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

import static util.Print.printf;

/**
 * @author:qiming
 * @date: 2021/10/31
 */
public class StackTest {
    @Test
    public void test() {
        queueTest(ArrayStack.class, 1000000);
        queueTest(LinkedListStack.class, 1000000);
    }

    public void queueTest(Class<?> cls, int N) {
        try {
            var constructor = cls.getConstructor();
            var rawInstance = constructor.newInstance();
            @SuppressWarnings("unchecked") var inst = (Stack<Integer>) rawInstance;
            int[] raw = Tools.gen(N, 1000000).stream().mapToInt(x -> x).toArray();
            var start = System.nanoTime();
            for (var i : raw) {
                inst.push(i);
            }
            for (int i = 0; i < N; i++) {
                inst.pop();
            }
            printf(rawInstance.getClass().getSimpleName()
                    + " time: %s\n", (System.nanoTime() - start) / 1_000_000.0 + " ms");
        } catch (NoSuchMethodException
                | InvocationTargetException
                | InstantiationException
                | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}

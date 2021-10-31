package algorithm.set;

import org.junit.Test;
import util.FileOperation;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import static util.Print.print;
import static util.Print.printf;

/**
 * @author:qiming
 * @date: 2021/10/31
 */
public class SetTest {
    @Test
    public void test() {
        queueTest(BSTSet.class);
        queueTest(LinkedListSet.class);
    }

    public void queueTest(Class<?> cls) {
        try {
            var constructor = cls.getConstructor();
            var rawInstance = constructor.newInstance();
            @SuppressWarnings("unchecked") var inst = (Set<String>) rawInstance;
            var start = System.nanoTime();
            ArrayList<String> arrayList = new ArrayList<>();
            FileOperation.readFile("algorithm/set/a-tale-of-two-cities.txt", arrayList);
            print(arrayList.size());
            for (String s : arrayList) {
                inst.add(s);
            }
            print(inst.size());
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

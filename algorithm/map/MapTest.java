package algorithm.map;

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
public class MapTest {
    @Test
    public void test() {
        queueTest(BSTMap.class);
        queueTest(LinkedListMap.class);
    }

    public void queueTest(Class<?> cls) {
        try {
            var constructor = cls.getConstructor();
            var rawInstance = constructor.newInstance();
            @SuppressWarnings("unchecked") var inst = (Map<String, Integer>) rawInstance;
            var start = System.nanoTime();
            ArrayList<String> arrayList = new ArrayList<>();
            FileOperation.readFile("algorithm/set/pride-and-prejudice.txt", arrayList);
            print("words: " + arrayList.size());
            for (String s : arrayList) {
                if (inst.contains(s)) {
                    inst.set(s, inst.get(s) + 1);
                } else {
                    inst.add(s, 1);
                }
            }
            print("distinct: " + inst.size());
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

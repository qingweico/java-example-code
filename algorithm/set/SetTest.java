package algorithm.set;

import cn.qingweico.io.FileUtils;
import cn.qingweico.io.Print;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import static cn.qingweico.io.Print.printf;

/**
 * @author zqw
 * @date 2021/10/31
 */
public class SetTest {
    @Test
    public void test() {
        setTest(BinarySearchTreeSet.class);
        setTest(AvlSet.class);
        setTest(LinkedListSet.class);
    }

    public void setTest(Class<?> cls) {
        try {
            var constructor = cls.getConstructor();
            var rawInstance = constructor.newInstance();
            @SuppressWarnings("unchecked") var inst = (Set<String>) rawInstance;
            var start = System.nanoTime();
            ArrayList<String> arrayList = new ArrayList<>();
            FileUtils.readFileToArrayList("algorithm/set/a-tale-of-two-cities.txt", arrayList);
            Print.println(arrayList.size());
            for (String s : arrayList) {
                inst.add(s);
            }
            Print.println(inst.size());
            printf(rawInstance.getClass().getSimpleName()
                    + " time: %s\n", (System.nanoTime() - start) / 1_000_000.0 + " ms");
            Print.println("===================================================");
        } catch (NoSuchMethodException
                | InvocationTargetException
                | InstantiationException
                | IllegalAccessException e) {
            Print.err(e.getMessage());
        }
    }
}

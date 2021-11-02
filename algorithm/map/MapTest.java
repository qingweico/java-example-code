package algorithm.map;

import algorithm.sort.Tools;
import org.junit.Test;
import util.FileOperation;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static util.Print.print;
import static util.Print.printf;

/**
 * @author:qiming
 * @date: 2021/10/31
 */
public class MapTest {
    ExecutorService exec = Executors.newFixedThreadPool(10);

    @Test
    public void test() {
        CountDownLatch latch = new CountDownLatch(4);
        exec.execute(() -> {
            mapTest(BSTMap.class);
            latch.countDown();
        });
        exec.execute(() -> {
            mapTest(AVLMap.class);
            latch.countDown();
        });
        exec.execute(() -> {
            mapTest(RedBlackTreeMap.class);
            latch.countDown();
        });

        exec.execute(() -> {
            mapTest(LinkedListMap.class);
            latch.countDown();
        });

        exec.shutdown();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void mapTest(Class<?> cls) {
        try {
            var constructor = cls.getConstructor();
            var rawInstance = constructor.newInstance();
            @SuppressWarnings("unchecked") var inst = (Map<String, Integer>) rawInstance;
            var start = System.nanoTime();
            ArrayList<String> arrayList = new ArrayList<>();
            FileOperation.readFile("algorithm/set/pride-and-prejudice.txt", arrayList);
            String clsName = rawInstance.getClass().getSimpleName();
            print(clsName + " words: " + arrayList.size());
            for (String s : arrayList) {
                if (inst.contains(s)) {
                    inst.set(s, inst.get(s) + 1);
                } else {
                    inst.add(s, 1);
                }
            }
            print(clsName + " distinct: " + inst.size());
            printf(clsName + " time: %s\n", (System.nanoTime() - start) / 1_000_000.0 + " ms" +
                    "\n===================================================");
        } catch (NoSuchMethodException
                | InvocationTargetException
                | InstantiationException
                | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void mapTest(Class<?> cls, int N) {
        try {
            var constructor = cls.getConstructor();
            var rawInstance = constructor.newInstance();
            @SuppressWarnings("unchecked") var inst = (Map<Integer, Integer>) rawInstance;
            var data = Tools.gen(N, Integer.MAX_VALUE);
            var start = System.nanoTime();
            for (var i : data) {
                inst.add(i, null);
            }
            for (var i : data) {
                inst.contains(i);
            }
            String clsName = rawInstance.getClass().getSimpleName();
            printf(clsName + " time: %s\n", (System.nanoTime() - start) / 1_000_000.0 + " ms");
        } catch (NoSuchMethodException
                | InvocationTargetException
                | InstantiationException
                | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}

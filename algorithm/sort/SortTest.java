package algorithm.sort;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

/**
 * @author:qiming
 * @date: 2021/10/16
 */
public class SortTest {

    @Test
    public void insert() {
        sortTest(InsertSort.class, 100000);
    }

    @Test
    public void select() {
        sortTest(SelectionSort.class, 100000);
    }

    @Test
    public void bubble() {
        sortTest(BubbleSort.class, 10000);
    }

    @Test
    public void merge() {
        sortTest(MergeSort.class, 10000);
    }

    @Test
    public void quick() {
        sortTest(QuickSort.class, 10000);
    }

    @Test
    public void mQuick() {
        sortTest(MQuickSort.class, 10);
    }

    @Test
    public void selSort() {
        sortTest(SelSort.class, 100);
    }
    @Test
    public void InsSort() {
        sortTest(InsSort.class, 100000);
    }

    public void sortTest(Class<?> cls, int N) {
        try {
            var constructor = cls.getConstructor();
            var rawInstance = constructor.newInstance();
            var start  = System.nanoTime();
            if (rawInstance instanceof IMutableSorter) {
                var A = Tools.gen(N);
                var instance = (IMutableSorter) rawInstance;
                A = instance.sort(A);
                System.out.format("time: %s\n", (System.nanoTime() - start) / 1_000_000.0 + " ms");
                Tools.assertSorted(A);
            } else if (rawInstance instanceof MutableSorter) {
                var A = Tools.gen(N).stream().mapToInt(x -> x).toArray();
                var instance = (MutableSorter) rawInstance;
                instance.sort(A);
                System.out.format("time: %s\n", (System.nanoTime() - start) / 1_000_000.0 + " ms");
                Tools.assertSorted(A);
            }else {
                int[] raw = Tools.gen(N, 10000).stream().mapToInt(x -> x).toArray();
                Integer[] A  = Arrays.stream(raw).boxed().toArray(Integer[]::new);
                // rawInstance.getClass().getMethod("sort", Object[].class).invoke(rawInstance, (Object[]) A);
                var instance = (InsSort)rawInstance;
                instance.sort(A);
                System.out.format("time: %s\n", (System.nanoTime() - start) / 1_000_000.0 + " ms");
                Tools.assertSorted(Arrays.asList(A));
            }
        } catch (NoSuchMethodException
                | InvocationTargetException
                | InstantiationException
                | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}

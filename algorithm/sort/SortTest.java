package algorithm.sort;

import org.junit.Test;
import thread.pool.CustomThreadPool;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

import static util.Print.printf;

/**
 * @author zqw
 * @date 2021/10/16
 */
public class SortTest {

    ExecutorService exec = CustomThreadPool.newFixedThreadPool(5, 10, 5);


    @Test
    public void insert() {
        CountDownLatch latch = new CountDownLatch(3);
        exec.execute(() -> {
            sortTest(InsSortImproved.class, 10000);
            latch.countDown();
        });
        exec.execute(() -> {
            sortTest(InsSort.class, 10000);
            latch.countDown();
        });
        exec.execute(() -> {
            sortTest(InsertSort.class, 10000);
            latch.countDown();
        });

        exec.shutdown();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void select() {
        sortTest(SelectionSort.class, 100000);
        sortTest(SelSort.class, 100000);
    }

    @Test
    public void bubble() {
        CountDownLatch latch = new CountDownLatch(2);
        exec.execute(() -> {
            sortTest(BubSort.class, 1000);
            latch.countDown();
        });
        exec.execute(() -> {
            sortTest(BubbleSort.class, 1000);
            latch.countDown();
        });
        exec.shutdown();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void merge() {
        CountDownLatch latch = new CountDownLatch(2);
        exec.execute(() -> {
            sortTest(MerSortBottomUp.class, 100000);
            latch.countDown();
        });
        exec.execute(() -> {
            sortTest(MerSortBottomUpImproved.class, 100000);
            latch.countDown();
        });
        exec.shutdown();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void shell() {
        sortTest(ShellSort.class, 1000000);
        sortTest(ShellSortImproved.class, 1000000);
        sortTest(MergeSort.class, 1000000);
    }

    @Test
    public void quick() {
        CountDownLatch latch = new CountDownLatch(4);
        exec.execute(() -> {
            sortTest(DualQuiSort.class, 100000);
            latch.countDown();
        });
        exec.execute(() -> {
            sortTest(HeapSort.class, 100000);
            latch.countDown();
        });
        exec.execute(() -> {
            sortTest(MerSort.class, 100000);
            latch.countDown();
        });
        exec.execute(() -> {
            sortTest(ThreeWaysQuiSort.class, 100000);
            latch.countDown();
        });
        exec.shutdown();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void q() {
        sortTest(DualQuiSort.class, 10000);
    }
    @Test
    public void heapSort() {
        CountDownLatch latch = new CountDownLatch(3);
        exec.execute(() -> {
            sortTest(HeapSort.class, 1000000);
            latch.countDown();
        });
        exec.execute(() -> {
            sortTest(HeapSortImproved.class, 1000000);
            latch.countDown();
        });
        exec.execute(() -> {
            sortTest(InPlaceHeapSort.class, 1000000);
            latch.countDown();
        });
        exec.shutdown();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sortTest(Class<?> cls, int n) {
        try {
            var constructor = cls.getConstructor();
            var rawInstance = constructor.newInstance();
            var start = System.nanoTime();
            if (rawInstance instanceof IMutableSorter) {
                var unordered = Tools.genList(n);
                var instance = (IMutableSorter) rawInstance;
                unordered = instance.sort(unordered);
                printf(rawInstance.getClass().getSimpleName() + " time: %s\n", (System.nanoTime() - start) / 1_000_000.0 + " ms");
                Tools.assertSorted(unordered);
            } else if (rawInstance instanceof MutableSorter) {
                var unordered = Tools.genList(n).stream().mapToInt(x -> x).toArray();
                var instance = (MutableSorter) rawInstance;
                instance.sort(unordered);
                printf(rawInstance.getClass().getSimpleName() + " time: %s\n", (System.nanoTime() - start) / 1_000_000.0 + " ms");
                Tools.assertSorted(unordered);
            } else {
                int[] raw = Tools.genList(n).stream().mapToInt(x -> x).toArray();
                Integer[] unordered = Arrays.stream(raw).boxed().toArray(Integer[]::new);
                rawInstance.getClass().getMethod("sort",
                        Comparable[].class).invoke(rawInstance, (Object) unordered);
                printf(rawInstance.getClass().getSimpleName() + " time: %s\n", (System.nanoTime() - start) / 1_000_000.0 + " ms");
                Tools.assertSorted(Arrays.asList(unordered));
            }
        } catch (NoSuchMethodException
                | InvocationTargetException
                | InstantiationException
                | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}

package algorithm.sort;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static util.Print.printf;

/**
 * @author:qiming
 * @date: 2021/10/16
 */
public class SortTest {

    ExecutorService exec = Executors.newFixedThreadPool(10);


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

    public void sortTest(Class<?> cls, int N) {
        try {
            var constructor = cls.getConstructor();
            var rawInstance = constructor.newInstance();
            var start = System.nanoTime();
            if (rawInstance instanceof IMutableSorter) {
                var A = Tools.gen(N);
                var instance = (IMutableSorter) rawInstance;
                A = instance.sort(A);
                printf(rawInstance.getClass().getSimpleName() + " time: %s\n", (System.nanoTime() - start) / 1_000_000.0 + " ms");
                Tools.assertSorted(A);
            } else if (rawInstance instanceof MutableSorter) {
                var A = Tools.gen(N, 100000).stream().mapToInt(x -> x).toArray();
                var instance = (MutableSorter) rawInstance;
                instance.sort(A);
                printf(rawInstance.getClass().getSimpleName() + " time: %s\n", (System.nanoTime() - start) / 1_000_000.0 + " ms");
                Tools.assertSorted(A);
            } else {
                int[] raw = Tools.gen(N, 100000).stream().mapToInt(x -> x).toArray();
                Integer[] A = Arrays.stream(raw).boxed().toArray(Integer[]::new);
                rawInstance.getClass().getMethod("sort",
                        Comparable[].class).invoke(rawInstance, (Object) A);
                printf(rawInstance.getClass().getSimpleName() + " time: %s\n", (System.nanoTime() - start) / 1_000_000.0 + " ms");
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

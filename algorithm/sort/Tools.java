package algorithm.sort;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author:qiming
 * @date: 2021/10/16
 */
public class Tools {
    static void swap(int[] A, int i, int j) {
        int tmp = A[i];
        A[i] = A[j];
        A[j] = tmp;
    }

    static <E> void swap(E[] A, int i, int j) {
        E tmp = A[i];
        A[i] = A[j];
        A[j] = tmp;
    }

    static List<Integer> gen(int N) {
        return gen(N, 1000);
    }

    static List<Integer> gen(int N, int bound) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            list.add((int) (Math.random() * bound));
        }
        return list;
    }
    static List<Integer> genOrder(int N) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            list.add(i);
        }
        return list;
    }

    static void assertSorted(int[] A) {
        assertSorted(Arrays.stream(A).boxed().collect(Collectors.toList()));
    }

    static void assertSorted(List<Integer> A) {
        int min = Integer.MIN_VALUE;
        for (var i : A) {
            if (min > i) {
                Assert.fail("Array not in sorted order!");
            }
            min = i;
        }
    }
}

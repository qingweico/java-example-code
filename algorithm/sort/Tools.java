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
        return gen(N, 10000);
    }

    static List<Integer> gen(int N, int bound) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            list.add((int) (Math.random() * bound));
        }
        return list;
    }

    static List<Integer> genOrder(int N) {
        return genOrder(N, N);
    }

    static List<Integer> genOrder(int N, int bound) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            if (N != bound) {
                list.add(bound);
            } else {
                list.add(i);
            }
        }
        return list;
    }

    public static void assertSorted(int[] A) {
        assertSorted(Arrays.stream(A).boxed().collect(Collectors.toList()));
    }

    public static void assertSorted(List<Integer> A) {
        assertSorted(A, false);
    }

    public static void assertSorted(List<Integer> A, boolean isDesc) {
        if (!isDesc) {
            int min = Integer.MIN_VALUE;
            for (var i : A) {
                if (min > i) {
                    Assert.fail("Array not in sorted order!");
                }
                min = i;
            }
        } else {
            int max = Integer.MAX_VALUE;
            for (var i : A) {
                if (max < i) {
                    Assert.fail("Array not in sorted order!");
                }
                max = i;
            }
        }
    }
}

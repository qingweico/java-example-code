package util;

import junit.framework.Assert;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 一些简单的算法辅助工具类
 *
 * @author zqw
 * @date 2021/10/16
 */
public class Tools {
    private static final char[] LETTERS = new char[]{
            'A', 'B', 'C', 'D', 'E', 'F', 'G',
            'H', 'I', 'J', 'K', 'L', 'M', 'n',
            'O', 'P', 'Q', 'R', 'S', 'T', 'U',
            'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g',
            'h', 'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't', 'u',
            'v', 'w', 'x', 'y', 'z'
    };

    public static void swap(int[] ai, int i, int j) {
        int tmp = ai[i];
        ai[i] = ai[j];
        ai[j] = tmp;

    }

    public static <E> void swap(E[] e, int i, int j) {
        E tmp = e[i];
        e[i] = e[j];
        e[j] = tmp;
    }

    public static List<Integer> genList(int n) {
        return genList(n, 1000000);
    }

    public static List<Integer> genList(int n, int bound) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add((int) (Math.random() * bound));
        }
        return list;
    }

    public static int[] genIntArray(int n, int bound) {
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            // random -> [1, bound)
            array[i] = RandomDataUtil.ri(bound);
        }
        return array;
    }

    public static float[] genFloatArray(int n, int bound) {
        float[] array = new float[n];
        for (int i = 0; i < n; i++) {
            array[i] = RandomDataUtil.rf(bound);
        }
        return array;
    }

    public static int[] genIntArray(int n) {
        return genIntArray(n, 100);
    }

    public static List<Integer> genOrder(int n) {
        return genOrder(n, false);
    }

    public static List<Integer> genOrder(int n, boolean same) {
        if (same) {
            int rnd = new Random().nextInt(n);
            return IntStream.iterate(rnd, x -> x)
                    .limit(n)
                    .boxed()
                    .collect(Collectors.toList());
        }
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add(i);
        }
        return list;
    }

    public static List<String> genString(int n, int lineWidth) {
        return genString(n, lineWidth, false);
    }

    public static List<String> genString(int n, int lineWidth, boolean isFixed) {
        List<String> list = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder(lineWidth);
        while (n > 0) {
            int bits;
            // String 是否为固定长度
            if (isFixed) {
                bits = lineWidth;
            } else {
                bits = (int) (Math.random() * lineWidth) + 1;
            }
            for (int i = 0; i < bits; i++) {
                int rndIndex = ThreadLocalRandom.current().nextInt(0, LETTERS.length);
                stringBuilder.append(LETTERS[rndIndex]);
            }
            n--;
            list.add(stringBuilder.toString());
            stringBuilder.delete(0, lineWidth);
        }
        return list;
    }

    public static void assertSorted(int[] ai) {
        assertSorted(Arrays.stream(ai).boxed().collect(Collectors.toList()));
    }

    public static void assertSorted(List<Integer> ci) {
        assertSorted(ci, false);
    }

    public static void assertSorted(List<Integer> ci, boolean isDesc) {
        if (!isDesc) {
            int min = Integer.MIN_VALUE;
            for (var i : ci) {
                if (min > i) {
                    Assert.fail("Array not in sorted order!");
                }
                min = i;
            }
        } else {
            int max = Integer.MAX_VALUE;
            for (var i : ci) {
                if (max < i) {
                    Assert.fail("Array not in sorted order!");
                }
                max = i;
            }
        }
    }
}

package algorithm.sort;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
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

    static void swap(int[] ai, int i, int j) {
        int tmp = ai[i];
        ai[i] = ai[j];
        ai[j] = tmp;

    }

    static <E> void swap(E[] e, int i, int j) {
        E tmp = e[i];
        e[i] = e[j];
        e[j] = tmp;
    }

    public static List<Integer> gen(int n) {
        return gen(n, 1000000);
    }

    public static List<Integer> gen(int n, int bound) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add((int) (Math.random() * bound));
        }
        return list;
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

    public static List<String> genString(int n, int lineWidth, boolean fixedLength) {
        List<String> list = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder(lineWidth);
        while (n > 0) {
            int bits;
            if (fixedLength) {
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

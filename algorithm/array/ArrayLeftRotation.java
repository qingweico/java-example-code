package algorithm.array;

import algorithm.sort.Tools;

import java.util.Arrays;

/**
 * @author zqw
 * @date 2022/7/18
 */
/*
 * A left rotation operation on an array
 * shifts each of the array's elements
 * given integer n unit to the left.
 */

public class ArrayLeftRotation {

    /**
     * Returns the result of left rotation of given array arr and integer n
     * @param arr : int[] given array
     * @param n : int given integer
     * @return : int[] result of left rotation
     */
    public static int[] rotateLeft(int[] arr, int n) {
        int size = arr.length;
        int[] dst = new int[size];
        n = n % size;
        for (int i = 0; i < size; i++) {
            dst[i] = arr[n];
            n = (n + 1) % size;
        }
        return dst;
    }

    public static void main(String[] args) {
        int[] arr = Tools.genArray(10,10);
        System.out.println(Arrays.toString(arr));
        int[] result = rotateLeft(arr, 3);
        System.out.println(Arrays.toString(result));
    }
}

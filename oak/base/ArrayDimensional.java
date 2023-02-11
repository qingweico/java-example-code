package oak.base;

import util.Print;

import java.util.Scanner;

/**
 * 二维数组
 * @author zqw
 * @date 2021/2/10
 */
class ArrayDimensional {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[][] arr = new int[3][2];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                arr[i][j] = sc.nextInt();
            }
        }
        for (int[] its : arr) {
            for (int anInt : its) {
                // 增强for循环遍历的对象数组不可以为null
                Print.prints(anInt);
            }
        }
        sc.close();
    }
}

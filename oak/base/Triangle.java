package oak.base;

import util.Print;

import java.util.Scanner;

/**
 * 打印金字塔
 *
 * @author zqw
 * @date 2021/2/10
 */
class Triangle {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int s = input.nextInt();
        int step = 2;
        int j;
        for (int i = 1; i <= s; i++) {
            for (j = 1; j <= (s - i) * step; j++) {
                Print.prints();
            }
            for (j = i; j > 1; j--) {
                Print.prints(j);
            }
            for (j = 1; j <= i; j++) {
                Print.prints(j);
            }
            Print.println();
        }
        input.close();
    }
}

package oak.base;

import java.util.Scanner;

/**
 * @author zqw
 * @date 2021/2/10
 */
class Binary {
    public static void main(String[] args) {
        toBinary(10);
        toDecimal();
    }

    public static void toBinary(int num) {
        StringBuilder s = new StringBuilder();
        while (num != 0) {
            s.insert(0, num % 2);
            num /= 2;
        }
        System.out.println(s);
    }

    public static void toDecimal() {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        int num = 0;
        int count = 0;
        for (int j = s.length() - 1; j >= 0; j--) {
            int i = Integer.parseInt(String.valueOf(s.charAt(j)));
            num += (int) Math.pow(2, count) * i;
            count++;
        }
        sc.close();
        System.out.println(num);
    }

    public static String api(int num) {
        // 十进制转换为二进制 API
        return Integer.toBinaryString(num);
    }
}

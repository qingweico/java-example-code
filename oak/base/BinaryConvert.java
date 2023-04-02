package oak.base;

import java.util.Scanner;

/**
 * 进制转换
 * @author zqw
 * @date 2021/2/10
 */
class BinaryConvert {
    public static void main(String[] args) {
        toBinary(10);
        toDecimal();
        System.out.println(decToBin(10));
        System.out.println(binToDec("10010"));
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

    public static String decToBin(int num) {
        // 十进制转换为二进制 API
        return Integer.toBinaryString(num);
    }
    public static String binToDec(String value) {
        // 二进制转换为十进制 API
        return Integer.valueOf(value, 2).toString();
    }
}

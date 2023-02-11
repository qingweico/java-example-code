package oak.base;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * @author zqw
 * @date 2021/2/10
 */
class StringOpe {
    public static void main(String[] args) {
        String str = reverse("String");
        // 字符串转换为字符数组
        char[] ch = str.toCharArray();
        System.out.println(Arrays.toString(ch));

        // 字符串转换为字节数组
        byte[] bs = str.getBytes();
        System.out.println(Arrays.toString(bs));


        str = "你好";
        // 字符串转换为字节
        byte[] strBytes = str.getBytes(StandardCharsets.UTF_8);
        // 以字节数组的形式输出
        System.out.println(Arrays.toString(strBytes));
        // 将字节数组转换为字符串
        str = new String(strBytes, StandardCharsets.UTF_8);
        // 默认GBK编码
        System.out.println(str);


        // UTF-8编汉字占用3个字节
        // UTF-16编码汉字和英文都占用2个字节
        // GBK编码汉字则占用2个字节
        // GBK相当于ASCII的扩展
        // 范围大小: GB2312 < GBK < GB18030
    }
    public static String reverse(String str) {
        StringBuilder newStr = new StringBuilder();
        for (int i = str.length() - 1; i >= 0; i--) {
            newStr.append(str.charAt(i));
        }
        return String.valueOf(newStr);
    }
}

package oak.base;

import java.util.Arrays;

/**
 * 使用String类中的Split方法可能会遇到的问题
 * {@link String#split(String, int)}
 * 第二个参数 limit 默认使用 0
 * 1 limit > 0 最多拆分 limit部分, 剩余的部分不做拆分
 * 2 limit = 0 忽略尾随空字符串
 * 3 limit < 0 拆分所有
 * 其他参考 Apache Common, Spring中StringUtils的Split方法
 *
 * @author zqw
 * @date 2025/2/8
 */
class StringSplit {

    public static void main(String[] args) {
        String target = "a&b&";
        String[] split = target.split("&");
        System.out.println(split.length);
        System.out.println(Arrays.toString(split));

        split = target.split("&", -1);
        System.out.println(split.length);
        System.out.println(Arrays.toString(split));
    }
}

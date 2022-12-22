package algorithm.stack.monotonous;

import util.Print;

import java.util.Arrays;
import java.util.Stack;

/**
 * 单调栈 (Monotonous Stack) 一种特殊的栈, 在栈的[先进后出]的基础上,要求从栈顶到
 * 栈底的元素是单调递增或者单调递减的
 * [由于栈顶到栈底中递增或者递减的选取不同,不再区别具体的单调递增栈和单调递减栈]
 * <p>
 * 选取栈顶到栈底单调递增的栈为单调递增栈
 * | 2 |
 * | 4 |
 * | 6 |
 * ----
 * 单调递增栈: 只有比栈顶元素小的元素才能直接进栈,否则需要将先将栈中比当前元素小的元素出栈
 * 再将当前元素入栈
 *
 * @author zqw
 * @date 2022/9/23
 */
public class RightFirstMax {
    // 问题
    // 数组元素是随机无序的 要求打印出所有元素右边第一个大于该元素的值
    // eg:
    // IN: [2, 6, 3, 8 ,10, 9]
    // OUT: [6, 8, 8, 10, -1, -1]

    public static void main(String[] args) {
        int[] eg = new int[]{2, 6, 3, 8, 10, 9};
        Integer[] array = Arrays.stream(resolve((eg))).boxed().toArray(Integer[]::new);
        Print.printArray(array);
    }

    public static int[] resolve(int[] e) {
        if (e == null || e.length == 0) {
            return e;
        }
        int[] ret = new int[e.length];
        int index = 0;
        Stack<Integer> stack = new Stack<>();
        for (int i : e) {
            while (!stack.isEmpty() && stack.peek() < i) {
                stack.pop();
                ret[index++] = i;
            }
            stack.push(i);
        }
        while (index < e.length) {
            ret[index++] = -1;
        }
        return ret;
    }
}

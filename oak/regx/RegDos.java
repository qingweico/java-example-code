package oak.regx;

import java.util.regex.Pattern;

/**
 * 正则表达式拒绝服务
 * 正则表达式 {@code /A(B|C+)+D/} 是一个典型的易受 ReDoS 攻击的模式
 * 安全的正则表达式编写原则
 * - 避免嵌套量词
 * - 避免模糊匹配
 * - 使用原子组
 * - 设置超时机制
 * parseHTML 的使用问题
 *
 * @author zqw
 * @date 2025/8/2
 */
class RegDos {
    static Pattern pattern = Pattern.compile("A(B|C+)+D");

    public static void main(String[] args) {
        long count = pattern.matcher("ACCCCCCCCCCCCCCCCCCCCCCCCCCCCCX").results().limit(1000).count();
        System.out.println(count);
    }
}

package oak.base;

/**
 * -XX:hashCode = 0 随机数
 * -XX:hashCode = 1 地址 + 随机数
 * -XX:hashCode = 2 固定值1
 * -XX:hashCode = 3 递增数列
 * -XX:hashCode = 4 地址
 * -XX:hashCode = [ 5 或者 其他] 随机数
 *
 * @author zqw
 * @date 2023/5/7
 * VM args: -XX:+UnlockExperimentalVMOptions -XX:hashCode=?
 */
class VariableHashCode {
    public static void main(String[] args) {
        Object o1 = new Object();
        Object o2 = new Object();
        Object o3 = new Object();
        System.out.println(Integer.toHexString(o1.hashCode()));
        System.out.println(Integer.toHexString(o2.hashCode()));
        System.out.println(Integer.toHexString(o3.hashCode()));
    }
}

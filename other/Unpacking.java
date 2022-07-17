package other;

/**
 * --------------- 包装类型拆箱时带来的空指针问题 ---------------
 *
 * @author zqw
 * @date 2022/7/16
 */
class Unpacking {
    @SuppressWarnings("all")
    public static Integer parseInt(String s) {
        return s == null ? (Integer) null : Integer.parseInt(s);
    }

    public static void main(String[] args) {
        // npe
        // parseInt(null): 三目运算符右边是int类型 左边需要转换为int类型 所以对null进行拆箱 调用
        // Integer::intValue() 引起npe
        System.out.println(parseInt("-1") + " " + parseInt(null) + " " + parseInt("1"));
    }
}

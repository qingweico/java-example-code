package jvm;

/**
 * @author zqw
 * @date 2021/10/20
 * @see ObjectLayout
 */
public class EmptyObject {
    @SuppressWarnings("unused")
    public static void main(String[] args) {
        // VM　options: -javaagent:lib\InstrumentationAgent.jar
        // 对象头(MarkWord) 占用8个字节
        // 类型指针(class point) 占用4个字节
        // 实例数据
        // 对齐填充
        // 16
        System.out.println(InstrumentationAgent.getObjectSize(new Object()));
        // 16
        System.out.println(InstrumentationAgent.getObjectSize(new int[0]));
        // 24
        System.out.println(InstrumentationAgent.getObjectSize(""));

        // 32
        System.out.println(InstrumentationAgent.getObjectSize(new Object() {
            int m;
            long n;
            boolean b;
            // 指针永远占用4个字节;和后面的字符串对象大小没有关系
            final String s = "System.out.println";
        }));

        int x = 1;
        // 16
        System.out.println(InstrumentationAgent.getObjectSize(x));
        Integer y = 1;
        // 16
        System.out.println(InstrumentationAgent.getObjectSize(y));
        double z = 1.0;
        // 24
        System.out.println(InstrumentationAgent.getObjectSize(z));
    }
}

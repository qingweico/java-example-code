package jvm;

/**
 * @author zqw
 * @date 2021/10/20
 */
public class EmptyObject {
    public static void main(String[] args) {
        // VM　options: -javaagent:lib\InstrumentationAgent.jar
        // 16
        System.out.println(InstrumentationAgent.getObjectSize(new Object()));
        // 16
        System.out.println(InstrumentationAgent.getObjectSize(new int[0]));
        // 24
        System.out.println(InstrumentationAgent.getObjectSize(""));

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

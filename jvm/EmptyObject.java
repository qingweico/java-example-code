package jvm;

/**
 * @author zqw
 * @date 2021/10/20
 */
public class EmptyObject {
    public static void main(String[] args) {
        // VM　options: -javaagent:lib\InstrumentationAgent.jar
        System.out.println(InstrumentationAgent.getObjectSize(new Object()));
        System.out.println(InstrumentationAgent.getObjectSize(new int[0]));
        System.out.println(InstrumentationAgent.getObjectSize(""));

        int x = 1;
        System.out.println(InstrumentationAgent.getObjectSize(x));
        Integer y = 1;
        System.out.println(InstrumentationAgent.getObjectSize(y));
        double z = 1.0;
        System.out.println(InstrumentationAgent.getObjectSize(z));
    }
}
